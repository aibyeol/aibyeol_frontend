package com.example.sunflower

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.sunflower.ui.viewModel.RecordingViewModel

import com.example.sunflower.ui.theme.SunflowerTheme

class MainActivity : ComponentActivity() {
    private val recordingViewModel: RecordingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunflowerTheme {
                //SunflowerApp( recordingViewModel = recordingViewModel )
                SunflowerApp()
            }
        }
    }
}

