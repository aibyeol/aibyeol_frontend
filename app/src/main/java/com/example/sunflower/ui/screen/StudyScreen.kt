package com.example.sunflower.ui.screen

import android.graphics.Paint.Align
import android.media.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sunflower.R
import com.example.sunflower.ui.theme.SunflowerTheme


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
) {
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
            DownloadImage("http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "hello study",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun StartPreview() {
    SunflowerTheme {
        StudyScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            downloadImageUrl = "",
            onNextButtonClicked = {},
        )
    }
}