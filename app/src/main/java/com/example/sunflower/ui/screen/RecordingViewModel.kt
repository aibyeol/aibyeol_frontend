package com.example.sunflower.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunflower.MainActivity
import java.io.File
import java.io.IOException

class RecordingViewModel : ViewModel() {
    private val _recordingState = MutableLiveData<RecordingState>(RecordingState.Stopped)
    val recordingState: LiveData<RecordingState> = _recordingState
    private var mediaRecorder: MediaRecorder? = null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    fun startRecording(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // Request permission in the activity
            ActivityCompat.requestPermissions(context as MainActivity, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
            Log.d("StartRecording","Permission Error")
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
                setOutputFile(getOutputFilePath(context))

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
        return File(storageDir, "recording_$timestamp.3gp").absolutePath
    }

    override fun onCleared() {
        super.onCleared()
        releaseRecorder()
    }

    private fun releaseRecorder() {
        mediaRecorder?.release()
        mediaRecorder = null
    }

    sealed class RecordingState {
        object Started : RecordingState()
        data class Error(val message: String?) : RecordingState()
        object Stopped : RecordingState()
    }
}