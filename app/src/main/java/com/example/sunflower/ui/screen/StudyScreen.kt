package com.example.sunflower.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint.Align
import android.media.Image
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sunflower.R
import com.example.sunflower.StudyTopBar
import com.example.sunflower.ui.theme.SunflowerTheme
import com.example.sunflower.ui.viewModel.RecordingViewModel
import com.example.sunflower.ui.viewModel.SurveyViewModel
import java.io.File
import java.io.IOException

@Composable
fun DownloadImage(downloadImageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(context= LocalContext.current)
            .data(downloadImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "hello",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun StudyScreen(
    modifier : Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {},
    surveyViewModel: SurveyViewModel = viewModel()
    //recordingViewModel: RecordingViewModel
) {
    val context = LocalContext.current
    //var isRecording by remember { mutableStateOf(false) }
    //added for viewModelManager
    val recordingViewModel = viewModel<RecordingViewModel>()
    val isButtonEnabled by surveyViewModel.isButtonEnabled.observeAsState(initial = false)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            /**
             * 로컬에 저장된 이미지를 띄우는 간단한 테스트 코드입니다.
             */
            /*
            Image(
                painter = painterResource(R.drawable.character01),
                contentDescription = null,
                modifier = Modifier.width(300.dp)
            )
            */
            //DownloadImage("http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg")
            val scenarioUrl by surveyViewModel.scenarioUrls.observeAsState()
            val scenario = scenarioUrl?.get(3)
            if (scenario != null) {
                Image(
                    painter = rememberAsyncImagePainter(scenario),
                    contentDescription = "Message Image",
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .size(200.dp)
                )
            } else {
                Text("No Image Available")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "시나리오가 끝났습니다." +
                        "상황에 맞는 알맞은 답변을 해주세요!",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            /**
             * 개발용 Recording Row 입니다.
             */
            /*
            Row(
                modifier = Modifier.padding(horizontal=8.dp)
            ) {
                Button(onClick = {
                viewModel.startRecording(context)
                }) {
                Text(text = "녹음 시작")
                }
                Button(onClick = {
                    viewModel.stopRecording()
                }) {
                    Text(text = "녹음 끝")
                }
                Button(onClick = {
                    viewModel.startPlayback()
                }) {
                    Text(text = "재생 시작")
                }
                Button(onClick = {
                    viewModel.stopPlayback()
                    viewModel.sendRecordingToServer()
                }) {
                    Text(text = "재생 끝")
                }
            }
            */
            /**
             * 실제 Recording Row 입니다.
             */
            var currentIndex by remember { mutableStateOf(0) }
            LaunchedEffect(currentIndex) {
                surveyViewModel.setButtonEnabled(false)
            }

            Row(
                modifier = Modifier.padding(horizontal=8.dp)
            ) {
                Button(onClick = {
                    recordingViewModel.startRecording(context)
                    surveyViewModel.setButtonEnabled(true) // Enable stop button after start
                },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFB8500),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "누르고 이야기하기!")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        recordingViewModel.stopRecording()
                        recordingViewModel.sendRecordingToServer()
                        surveyViewModel.setButtonEnabled(false) // Disable stop button after stop
                    },
                    enabled = isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFB8500),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "이야기 끝!")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    onNextButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
                //enabled = isButtonEnabled
            ) {
                Text("결과 보기!")
            }
        }
    }
}

@Preview
@Composable
fun StudyPreview() {
    StudyScreen()
}