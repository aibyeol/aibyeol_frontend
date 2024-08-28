package com.example.sunflower.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sunflower.SunflowerScreen
import com.example.sunflower.ui.theme.SunflowerTheme
import com.example.sunflower.R

@Preview
@Composable
fun PreviewButton() {
    SunflowerTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("아이별_demo") }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { UserScreen(navController) }
                composable("loginComplete") { LoginCompleteScreen() }
            }
        }
    }
}

@Composable
fun UserScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(SunflowerScreen.Survey.name)
                }) {
                    Text("OK")
                }
            },
            title = { Text(text = "환영합니다!") },
            text = { Text("로그인에 성공했습니다.") }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 배경 이미지 추가
        Image(
            painter = painterResource(id = R.drawable.background), // 배경 이미지 리소스 ID 사용
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop, // 이미지를 화면 크기에 맞게 크롭
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // 이메일 입력 필드
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("username@gmail.com") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 비밀번호 입력 필드
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("********") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 로그인 버튼
            Button(
                onClick = {
                    showDialog = true // 다이얼로그 표시
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun LoginCompleteScreen() {
    // 화면 중앙에 "로그인이 완료되었습니다" 메시지 표시
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "로그인이 완료되었습니다",
            color = Color.Green,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ButtonEx() {
    Column(
        modifier = Modifier
            .padding(48.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp), // 수직간격
        horizontalAlignment = Alignment.CenterHorizontally, // 센터 위치
    ) {
        Text("Filled button:")
        FilledButtonExample(
            onClick = { Log.d("Filled button", "Filled button clicked.") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Filled tonal button:")
        FilledTonalButtonExample(onClick = { Log.d("Filled tonal button", "Filled tonal button clicked.") })
        Text("Elevated button:")
        ElevatedButtonExample(onClick = { Log.d("Elevated button", "Elevated button clicked.") })
        Text("Outlined button:")
        OutlinedButtonExample(onClick = { Log.d("Outlined button", "Outlined button clicked.") })
        Text("Text button")
        TextButtonExample(onClick = { Log.d("Text button", "Text button clicked.") })
    }
}

@Composable
fun FilledButtonExample(onClick: () -> Unit, modifier: Modifier) {
    Button(onClick = { onClick() }, modifier = modifier) {
        Text("login")
    }
}

@Composable
fun FilledTonalButtonExample(onClick: () -> Unit) {
    FilledTonalButton(onClick = { onClick() }) {
        Text("Tonal")
    }
}

@Composable
fun ElevatedButtonExample(onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
        Text("Elevated")
    }
}

@Composable
fun OutlinedButtonExample(onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }) {
        Text("Outlined")
    }
}

@Composable
fun TextButtonExample(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }) {
        Text("Text Button")
    }
}
