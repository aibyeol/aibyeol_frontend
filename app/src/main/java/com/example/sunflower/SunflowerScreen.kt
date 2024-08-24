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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sunflower.data.SampleData
import com.example.sunflower.ui.screen.RecordingViewModel
import com.example.sunflower.ui.screen.StudyScreen
import com.example.sunflower.ui.screen.StudyViewModel
import com.example.sunflower.ui.screen.SurveyScreen

/**
 * 네이게이션에서 사용할 상태 목록입니다.
 * Start는 아직 사용되지 않았는데, 로그인 화면이 될 예정입니다.
 */
enum class SunflowerScreen() {
    Survey,
    Study
}

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

/**
 * [StudyViewModel]은 설문과 학습 정보 등 앱 실행 과정에서 발생하는 각종 데이터를 저장합니다.
 * com.example.sunflower.ui.(폴더생성: 아마도 model)StudyViewModel.kt로 파일 생성 예정
 * com.example.sunflower.ui.StudyViewModel 생성 완료
 */
@Composable
fun SunflowerApp(
    navController: NavHostController = rememberNavController(),
    recordingViewModel: RecordingViewModel
) {
    Scaffold(
        topBar = {
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
                    }
                )
            }

            composable(route = SunflowerScreen.Study.name) {
                StudyScreen(
                    downloadImageUrl = "",
                    onNextButtonClicked = {
                        navController.navigate(SunflowerScreen.Study.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    recordingViewModel = recordingViewModel
                )
            }
        }
    }
}
