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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.request.ImageRequest
import com.example.sunflower.R
import com.example.sunflower.SunflowerScreen
import com.example.sunflower.ui.theme.SunflowerTheme
import com.example.sunflower.ui.viewModel.RecordingViewModel
import java.io.File
import java.io.IOException

@Preview
@Composable
fun StudyPreview(){
    StudyScreen(
        downloadImageUrl = "",
        onNextButtonClicked = {
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        //viewModel = recordingViewModel
    )
}
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
    downloadImageUrl: String,
    onNextButtonClicked: () -> Unit = {},
    //recordingViewModel: RecordingViewModel
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    //added for viewModelManager
    val viewModel = viewModel<RecordingViewModel>()

    StudyTopBar()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
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
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "hello study",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.startRecording(context)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "녹음 시작")
            }
            Button(onClick = {
                viewModel.stopRecording()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "녹음 끝")
            }
            Button(onClick = {
                viewModel.startPlayback()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "재생 시작")
            }
            Button(onClick = {
                viewModel.stopPlayback()
                viewModel.sendRecordingToServer()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
            ) {
                Text(text = "재생 끝")
            }
        }
    }
}

@Composable
fun StudyTopBar(
    title: String = "",
    onMenuClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFFB8500))
    )
}