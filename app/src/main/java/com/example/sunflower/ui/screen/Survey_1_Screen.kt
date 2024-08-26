package com.example.sunflower.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.example.sunflower.R

@Composable
fun Survey1Screen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE08A)) // 배경색 설정
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // AppBar와 유사한 상단 UI (스텝바)
        TopBar()

        Spacer(modifier = Modifier.height(24.dp))

        // 별 이미지 및 질문 텍스트
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.star_icon),
                contentDescription = "Star Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "아이의 성별을 선택해 주세요",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 성별 선택 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GenderOptionButton(
                text = "남자아이",
                iconResId = R.drawable.boy, // 남자아이 아이콘 리소스
                backgroundColor = Color(0xFFFFE4B2), // 버튼 색상 설정
                onClick = { /* TODO: Handle boy option selection */ }
            )
            GenderOptionButton(
                text = "여자아이",
                iconResId = R.drawable.girl, // 여자아이 아이콘 리소스
                backgroundColor = Color(0xFFFFE4B2), // 버튼 색상 설정
                onClick = { /* TODO: Handle girl option selection */ }
            )
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        IconButton(onClick = { /* TODO: Handle navigation drawer opening */ }) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_menu), // 메뉴 아이콘
//                contentDescription = "Menu Icon"
//            )
//        }
        Text(
            text = "설문조사",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
//        IconButton(onClick = { /* TODO: Handle profile action */ }) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_profile), // 프로필 아이콘
//                contentDescription = "Profile Icon"
//            )
//        }
    }

    // 스텝바 UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StepIndicator(stepNumber = 1, isActive = true)
        StepIndicator(stepNumber = 2, isActive = false)
        StepIndicator(stepNumber = 3, isActive = false)
        StepIndicator(stepNumber = 4, isActive = false)
    }
}

@Composable
fun StepIndicator(stepNumber: Int, isActive: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stepNumber.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) Color(0xFFFFA500) else Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Step $stepNumber",
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (isActive) Color(0xFFFFA500) else Color.Gray
            )
        )
    }
}

@Composable
fun GenderOptionButton(
    text: String,
    iconResId: Int,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp)
            .size(150.dp), // 버튼 크기 조정
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}
