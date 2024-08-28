package com.example.sunflower

import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sunflower.data.SampleData
import com.example.sunflower.ui.screen.RecordingViewModel
import com.example.sunflower.ui.screen.StudyScreen
import com.example.sunflower.ui.screen.SurveyScreen
import com.example.sunflower.ui.screen.UserScreen
import com.example.sunflower.ui.screen.InitScreen
import com.example.sunflower.ui.screen.Survey1Screen

enum class SunflowerScreen {
    Init,
    User,
    Survey,
    Survey1,
    Study
}

@Composable
fun SunflowerAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("아이별_demo") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFFFFA500)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                IconButton(onClick = onMenuClicked) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            IconButton(onClick = onProfileClicked) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }
    )
}

@Composable
fun SunflowerApp(
    navController: NavHostController = rememberNavController(),
    recordingViewModel: RecordingViewModel
) {
    Scaffold(
        topBar = {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute != null && currentRoute != SunflowerScreen.Init.name) {
                SunflowerAppBar(
                    canNavigateBack = false,
                    navigateUp = { /* TODO: implement back navigation */ },
                    onMenuClicked = { /* TODO: implement menu action */ },
                    onProfileClicked = { /* TODO: implement profile action */ }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SunflowerScreen.Init.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SunflowerScreen.Init.name) {
                InitScreen(navController = navController)
            }

            composable(route = SunflowerScreen.User.name) {
                UserScreen(navController = navController)
            }

            composable(route = SunflowerScreen.Survey.name) {
                SurveyScreen(
                    modifier = Modifier,
                    messages = SampleData.conversationSample,
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name)
                    }
                )
            }

            composable(route = SunflowerScreen.Survey1.name) {
                Survey1Screen(navController = navController)
            }

            composable(route = SunflowerScreen.Study.name) {
                StudyScreen(
                    navController = navController,
                    downloadImageUrl = "",
                    recordingViewModel = recordingViewModel
                )
            }
        }
    }
}
