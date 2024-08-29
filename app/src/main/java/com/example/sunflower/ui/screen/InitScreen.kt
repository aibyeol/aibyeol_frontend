package com.example.sunflower.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sunflower.R
import com.example.sunflower.SunflowerScreen

val STHupoFontFamily = FontFamily(
    Font(R.font.sthupo, FontWeight.Normal, FontStyle.Normal)
)

@Composable
fun InitScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 중앙의 별 이미지
            Image(
                painter = painterResource(id = R.drawable.star_icon), // 아이콘 리소스 ID 사용
                contentDescription = "Aibyeol Icon",
                modifier = Modifier.size(150.dp), // 이미지 크기 조정
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 텍스트 배치
            Text(
                text = "Aibyeol",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = STHupoFontFamily // STHupo 폰트 설정
                ),
                color = Color(0xFFFFA500) // 주황색(#FFA500) 적용
            )

            Spacer(modifier = Modifier.height(48.dp))

            // 시작하기 버튼
            Button(
                onClick = {
                    onNextButtonClicked() // 로그인 화면으로 네비게이션
                },
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
            ) {
                Text(
                    text = "시작하기",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
                )
            }
        }
    }
}