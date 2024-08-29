package com.example.sunflower.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sunflower.R

@Preview
@Composable
fun RewardPreview(){
    RewardScreen()
}

@Composable
fun RewardScreen(
    onNextButtonClicked : () -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxSize(), // or any other modifier you need
        contentAlignment = Alignment.Center // Centers the child composable
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center the content horizontally within the Column
            verticalArrangement = Arrangement.Center // Optional: Align items centrally in the Column
        ) {
            Image(
                painter = painterResource(id = R.drawable.star_reward), // Your image resource
                contentDescription = "Centered Image",
                modifier = Modifier
                    .size(250.dp) // Size of the image
            )
            Text(
                text = "정답입니다! 별 하나를 획득했어요",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(12.dp)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(), // or any other modifier you need
//        contentAlignment = Alignment.BottomCenter // Centers the child composable
    ) {
        Button(
            onClick = { onNextButtonClicked() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFDF0D5),
                contentColor = Color(0xFF023047)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp)
                .offset(y=(-30).dp)
                .fillMaxWidth()
        ) {
            Text("별 담으러 가기")
        }

    }

}