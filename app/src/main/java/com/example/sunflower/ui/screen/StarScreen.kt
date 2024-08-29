package com.example.sunflower.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sunflower.R

@Preview
@Composable
fun StarPreview(){
    StarScreen()
}

@Composable
fun StarScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 배경 이미지 설정
        Image(
            painter = painterResource(id = R.drawable.star_sky), // 배경 이미지
            contentDescription = null,
            modifier = Modifier.fillMaxSize(), // 화면 전체 크기로 설정
            contentScale = ContentScale.Crop  // 이미지 크롭으로 맞추기
        )
    }

}