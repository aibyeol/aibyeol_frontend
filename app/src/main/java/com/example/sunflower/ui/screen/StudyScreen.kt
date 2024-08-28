package com.example.sunflower.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sunflower.SunflowerScreen
import com.example.sunflower.ui.screen.RecordingViewModel

@Composable
fun DownloadImage(downloadImageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(downloadImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Downloaded Image",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun StudyScreen(
    navController: NavController, // NavController를 받아서 사용합니다.
    downloadImageUrl: String,
    recordingViewModel: RecordingViewModel,
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 이미지 다운로드 및 표시
            DownloadImage(downloadImageUrl)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "hello study",
                style = MaterialTheme.typography.headlineSmall
            )

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

            Spacer(modifier = Modifier.height(16.dp))

            // Next 버튼
            Button(
                onClick = {
                    navController.navigate(SunflowerScreen.User.name) // 원하는 다른 화면으로 네비게이션
                }
            ) {
                Text("Next")
            }
        }
    }
}
