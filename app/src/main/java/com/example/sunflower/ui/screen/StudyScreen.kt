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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sunflower.R
import com.example.sunflower.ui.theme.SunflowerTheme
import java.io.File
import java.io.IOException
<<<<<<< HEAD
=======
import androidx.navigation.NavController // NavController import 추가
import com.example.sunflower.SunflowerScreen // SunflowerScreen import 추가
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)

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
<<<<<<< HEAD
    modifier : Modifier = Modifier,
    downloadImageUrl: String,
    onNextButtonClicked: () -> Unit = {},
=======
    navController: NavController,
    modifier: Modifier = Modifier,
    downloadImageUrl: String,
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
    recordingViewModel: RecordingViewModel
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }

<<<<<<< HEAD

    Column(
        modifier = modifier,
=======
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
<<<<<<< HEAD
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
            DownloadImage("http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg")
            Spacer(modifier = Modifier.height(16.dp))
=======

            // Image 다운로드 및 표시
            DownloadImage(downloadImageUrl)

            Spacer(modifier = Modifier.height(16.dp))

>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
            Text(
                text = "hello study",
                style = MaterialTheme.typography.headlineSmall
            )
<<<<<<< HEAD
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                recordingViewModel.startRecording(context)
            }) {
                Text(text = "녹음 시작")
            }
            Button(onClick = {
                recordingViewModel.stopRecording()
            }) {
                Text(text = "녹음 끝")
            }
=======

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { recordingViewModel.startRecording(context) }) {
                Text(text = "녹음 시작")
            }

            Button(onClick = { recordingViewModel.stopRecording() }) {
                Text(text = "녹음 끝")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Next 버튼 추가
            Button(
                onClick = {
                    navController.navigate(SunflowerScreen.User.name) // 원하는 다른 화면으로 네비게이션
                }
            ) {
                Text("Next")
            }
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
        }
    }
}