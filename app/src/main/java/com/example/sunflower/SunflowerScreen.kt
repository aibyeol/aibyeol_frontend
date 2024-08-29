package com.example.sunflower

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sunflower.data.repository.SampleData
import com.example.sunflower.ui.screen.InitScreen
import com.example.sunflower.ui.screen.RewardScreen
import com.example.sunflower.ui.screen.StudyScreen
import com.example.sunflower.ui.viewModel.StudyViewModel
import com.example.sunflower.ui.screen.SurveyScreen
import com.example.sunflower.ui.screen.UserScreen
import com.example.sunflower.ui.viewModel.SurveyViewModel

/**
 * 네이게이션에서 사용할 상태 목록입니다.
 * Start는 아직 사용되지 않았는데, 로그인 화면이 될 예정입니다.
 */
enum class SunflowerScreen() {
    Init,
    User,
    Survey,
    Study,
    Reward
}

/**
 * AppBar는 원래 Back Button 하나만 구현할 예정이었습니다.
 * 08/29 : 설정과 메뉴 버튼을 담는 것으로 변경되었습니다.
 * Back Button은 추후 Bottom에 AppBar를 하나 더 만들어 구현할 계획입니다.
@Composable
fun SunflowerAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("아이별_demo") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
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
            }
        }
    )
}
**/
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

/**
 * [StudyViewModel]은 설문과 학습 정보 등 앱 실행 과정에서 발생하는 각종 데이터를 저장합니다.
 * com.example.sunflower.ui.(폴더생성: 아마도 model)StudyViewModel.kt로 파일 생성 예정
 * com.example.sunflower.ui.StudyViewModel 생성 완료
 */
@Composable
fun SunflowerApp(
    navController: NavHostController = rememberNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val surveyViewModel: SurveyViewModel = viewModel()

    Scaffold(
        topBar = {
            if (currentRoute != SunflowerScreen.Init.name
                && currentRoute != SunflowerScreen.User.name) {
                StudyTopBar()
            }
        }
    ) {
            innerPadding ->
        /**
         * uiState를 저장했던 예전 viewModel의 잔재. 추후 RecordingViewModel에 통합될 수 있음.
         */
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = SunflowerScreen.Init.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SunflowerScreen.Init.name) {
                InitScreen(
                    modifier = Modifier,
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.User.name)
                    }
                )
            }

            composable(route = SunflowerScreen.User.name) {
                UserScreen(
                    onLoginConfirmed = {
                        navController.navigate(SunflowerScreen.Survey.name)
                    }
                )
            }

            composable(route = SunflowerScreen.Survey.name) {
                SurveyScreen(
                    modifier = Modifier,
                    SampleData.conversationSample,
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name)
                    },
                    viewModel = surveyViewModel
                )
            }

            composable(route = SunflowerScreen.Study.name) {
                StudyScreen(
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Reward.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    surveyViewModel = surveyViewModel
                    //viewModel = recordingViewModel
                )
            }

            composable(route = SunflowerScreen.Reward.name) {
                RewardScreen()
            }
        }
    }
}
