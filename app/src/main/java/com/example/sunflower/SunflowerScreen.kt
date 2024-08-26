package com.example.sunflower

import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
<<<<<<< HEAD
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
=======
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sunflower.data.SampleData
import com.example.sunflower.ui.screen.RecordingViewModel
import com.example.sunflower.ui.screen.StudyScreen
<<<<<<< HEAD
import com.example.sunflower.ui.screen.StudyViewModel
import com.example.sunflower.ui.screen.SurveyScreen

/**
 * 네이게이션에서 사용할 상태 목록입니다.
 * Start는 아직 사용되지 않았는데, 로그인 화면이 될 예정입니다.
 */
enum class SunflowerScreen() {
    Survey,
=======
import com.example.sunflower.ui.screen.SurveyScreen
import com.example.sunflower.ui.screen.UserScreen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.graphics.Color
import com.example.sunflower.ui.screen.InitScreen // InitScreen을 추가
import com.example.sunflower.ui.screen.Survey1Screen // Survey_1_Screen 추가

enum class SunflowerScreen {
    Init,  // InitScreen 추가
    User,
    Survey,
    Survey1,
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
    Study
}

@Composable
fun SunflowerAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
<<<<<<< HEAD
=======
    onMenuClicked: () -> Unit,
    onProfileClicked: () -> Unit,
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("아이별_demo") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
<<<<<<< HEAD
            containerColor = MaterialTheme.colorScheme.primaryContainer
=======
            containerColor = Color(0xFFFFA500) // 주황색(#FFA500) 설정
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
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
<<<<<<< HEAD
=======
            } else {
                IconButton(onClick = onMenuClicked) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            IconButton(onClick = onProfileClicked) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
            }
        }
    )
}

<<<<<<< HEAD
/**
 * [StudyViewModel]은 설문과 학습 정보 등 앱 실행 과정에서 발생하는 각종 데이터를 저장합니다.
 * com.example.sunflower.ui.(폴더생성: 아마도 model)StudyViewModel.kt로 파일 생성 예정
 * com.example.sunflower.ui.StudyViewModel 생성 완료
 */
=======
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
@Composable
fun SunflowerApp(
    navController: NavHostController = rememberNavController(),
    recordingViewModel: RecordingViewModel
) {
    Scaffold(
        topBar = {
<<<<<<< HEAD
            SunflowerAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }
    ) {
            innerPadding ->
        /**
         * uiState를 저장했던 예전 viewModel의 잔재. 추후 RecordingViewModel에 통합될 수 있음.
         */
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = SunflowerScreen.Survey.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SunflowerScreen.Survey.name) {
                SurveyScreen(
                    modifier = Modifier,
                    SampleData.conversationSample,
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name)
=======
            // currentBackStackEntry가 null이 아니고, InitScreen이 아닌 경우에만 AppBar 표시
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
            // InitScreen 추가
            composable(route = SunflowerScreen.Init.name) {
                InitScreen(
                    navController = navController // navController 전달
                )
            }

            composable(route = SunflowerScreen.User.name) {
                UserScreen(
                    navController = navController // 수정된 부분: navController를 전달
                )
            }

            composable(route = SunflowerScreen.Survey.name) {
                SurveyScreen(
                    modifier = Modifier,
                    messages = SampleData.conversationSample,
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name) // StudyScreen으로 이동
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
                    }
                )
            }

<<<<<<< HEAD
            composable(route = SunflowerScreen.Study.name) {
                StudyScreen(
                    downloadImageUrl = "",
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
=======
            // Survey_1_Screen 추가
            composable(route = SunflowerScreen.Survey1.name) {
                Survey1Screen(navController = navController)
            }

            composable(route = SunflowerScreen.Study.name) {
                StudyScreen(
                    navController = navController, // 수정된 부분: navController를 전달
                    downloadImageUrl = "",
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
                    recordingViewModel = recordingViewModel
                )
            }
        }
    }
}
