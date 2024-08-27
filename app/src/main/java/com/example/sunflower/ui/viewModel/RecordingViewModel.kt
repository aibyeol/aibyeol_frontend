package com.example.sunflower.ui.viewModel

import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunflower.MainActivity
import com.example.sunflower.data.api.ApiService
import com.example.sunflower.data.api.ApiServiceSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Okio
import okio.sink
import java.io.File
import java.io.IOException

class RecordingViewModel() : ViewModel() {
    private val _recordingState = MutableLiveData<RecordingState>(RecordingState.Stopped)

    private val apiService by lazy { ApiServiceSingleton.apiService }
    //val recordingState: LiveData<RecordingState> = _recordingState
    private var fileName: String = ""
    private var mediaRecorder: MediaRecorder? = null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var playbackMediaPlayer: MediaPlayer? = null

    fun startRecording(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission in the activity
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            Log.d("StartRecording", "Permission Error")
        }
        try {
            initializeRecorder(context)
            mediaRecorder?.start()
            _recordingState.value = RecordingState.Started
        } catch (e: Exception) {
            _recordingState.value = RecordingState.Error(e.localizedMessage)
            Log.e("RecordingViewModel", "Error starting recording")
        }
    }

    fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            // setOutputFile()에서 설정한 파일 경로
            mediaRecorder = null
            _recordingState.value = RecordingState.Stopped
        } catch (e: Exception) {
            Log.e("RecordingViewModel", "Error stopping recording", e)
        }
    }

    private fun initializeRecorder(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(context).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                fileName = getOutputFilePath(context)
                setOutputFile(fileName)

                try {
                    prepare()
                } catch (e: IOException) {
                    Log.e("initialize", "prepare() failed")
                }
            }
        } else {
            Log.e("MediaRecorder", "Too Old Sdk Version")
            return
        }
    }

    private fun getOutputFilePath(context: Context): String {
        val timestamp = System.currentTimeMillis()
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: return ""
        Log.d("MediaRecorder", "External FilePath $storageDir")
        return File(storageDir, "recording_$timestamp.mp3").absolutePath
    }

    fun startPlayback(savedFilePath: String = fileName) {

        if (playbackMediaPlayer == null) {
            playbackMediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(savedFilePath)
                    prepareAsync() // Prepare asynchronously to avoid blocking UI thread
                    setOnPreparedListener { MediaPlayer ->
                        MediaPlayer.start()
                        _recordingState.value = RecordingState.Playing
                    }
                    setOnCompletionListener {
                        stopPlayback()
                        _recordingState.value = RecordingState.Stopped
                    }
                } catch (e: Exception) {
                    Log.e("RecordingViewModel", "Error preparing playback", e)
                    _recordingState.value = RecordingState.Error(e.localizedMessage)
                }
            }
        } else {
            // Playback already started, do nothing or handle as needed (e.g., seek)
        }
    }

    fun stopPlayback() {
        playbackMediaPlayer?.apply {
            stop()
            release()
            playbackMediaPlayer = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        releaseRecorder()
    }

    private fun releaseRecorder() {
        mediaRecorder?.release()
        mediaRecorder = null
    }

    fun sendRecordingToServer() {
        if (fileName.isEmpty()) {
            _recordingState.value = RecordingState.Error("No recording available")
            return
        }

        val file = File(fileName)

        val requestBody =
            file.asRequestBody("audio/mp3".toMediaTypeOrNull()) // "image/jpeg".toMediaType() 로 변경
        val multipartBody = MultipartBody.Part.createFormData("audio", file.name, requestBody)
        Log.d("RecordingViewModel", "File is $file")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.uploadAudio(multipartBody)
                if (response.isSuccessful) {
                    val source = response.body()?.source()
                    val buffer = source?.buffer
                    val tempFile = File.createTempFile("audio", ".mp3")
                    val sink = tempFile.sink()
                    buffer?.use {
                        sink.use {
                            buffer.readAll(sink)
                        }
                    }
                    // 서버로부터 성공 응답 받은 경우 처리
                    Log.d("RecordingViewModel", "Successfully Received AudioFile")
                    // 업로드 성공 시 필요한 작업 수행 (예: 토스트 메시지 표시)
                    withContext(Dispatchers.Main) {
                        stopPlayback()
                        startPlayback(tempFile.absolutePath)
                        _recordingState.value = RecordingState.PlayingDownloadedFile
                    }
                } else {
                    // 서버로부터 에러 응답 받은 경우 처리
                    Log.e("RecordingViewModel", "Upload failed: ${response.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        _recordingState.value = RecordingState.Error("Failed to upload recording")
                    }
                }
            } catch (e: Exception) {
                Log.e("RecordingViewModel", "Error sending recording to server", e)
                withContext(Dispatchers.Main) {
                    _recordingState.value = RecordingState.Error("Failed to upload recording")
                }
            }
        }
    }

    sealed class RecordingState {
        object Started : RecordingState()
        data class Error(val message: String?) : RecordingState()
        object Stopped : RecordingState()
        object Playing : RecordingState()
        object PlayingDownloadedFile : RecordingState()
    }
}