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
import androidx.lifecycle.ViewModel
import com.example.sunflower.MainActivity

class RecordingViewModel : ViewModel() {
    private var mediaRecorder: MediaRecorder? = null
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    fun startRecording(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청 (생략)
            ActivityCompat.requestPermissions(context as MainActivity, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
            Log.d("startRecording","get credentials first")
            return
        }
        // ... 녹음 시작 로직
        //mediaRecorder = new MediaRecorder(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(context).apply {
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(getOutputFilePath())
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
                Log.d("mediarecorder","now working")
            }
        } else {
            Log.e("mediarecorder", "version error")
        }
        // mediaRecorder를 어딘가에 저장하여 녹음 중지 시 사용
    }

    fun stopRecording() {
        // ... 녹음 중지 로직
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        Log.e("stopRecording", "stop recording")
    }

    private fun getOutputFilePath(): String {
        // 녹음 파일 저장 경로 설정 (외부 저장소 등)
        val timestamp = System.currentTimeMillis()
        return Environment.getExternalStorageDirectory().absolutePath + "/recording_$timestamp.3gp"
    }
}