package com.example.sunflower.ui.screen

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.sunflower.data.api.ApiService
import com.example.sunflower.data.repository.ImageSelectionData
import com.example.sunflower.R
import com.example.sunflower.data.api.ApiServiceSingleton
import com.example.sunflower.data.api.IdentityServiceSingleton
import com.example.sunflower.data.repository.Message
import com.example.sunflower.data.repository.MessageType
import com.example.sunflower.data.repository.SampleData
//import com.example.sunflower.data.repository.identityList
import com.example.sunflower.data.repository.identityMap
//import com.example.sunflower.data.repository.imageNames
import com.example.sunflower.ui.theme.SunflowerTheme
import com.example.sunflower.ui.viewModel.RecordingViewModel
import com.example.sunflower.ui.viewModel.SurveyViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * SampleData.kt의 conversationsample list 안에 들어있는 데이터입니다.
 **/
@Composable
fun SurveyScreen(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    onNextButtonClicked: () -> Unit = {},
    viewModel: SurveyViewModel
) {
    var currentIndex by remember { mutableStateOf(0) }
    //var isButtonEnabled by remember { mutableStateOf(true) }
    //val viewModel = viewModel<SurveyViewModel>()
    val isButtonEnabled by viewModel.isButtonEnabled.observeAsState(initial = false)

    LaunchedEffect(currentIndex) {
        val currentMessage = messages[currentIndex]
        if (currentMessage.messageType != MessageType.IMAGE_GRID) {
            viewModel.setButtonEnabled(true)
        } else {
            viewModel.setButtonEnabled(false)
        }
    }
    //배경이미지 삽입
    Image(
        painter = painterResource(id = R.drawable.chat_image), // 배경 이미지
        contentDescription = null,
        modifier = Modifier.fillMaxSize(), // 화면 전체 크기로 설정
        contentScale = ContentScale.Crop  // 이미지 크롭으로 맞추기
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        MessageCard(
            msg = messages[currentIndex],
            viewModel = viewModel,
            onImageGridDisplayed = {},
            onResponseReceived = { isSuccessful ->
                viewModel.setButtonEnabled(isSuccessful)
            }
        )
        Spacer(modifier = Modifier.weight(1f))  // Pushes buttons to the bottom

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (currentIndex < messages.size - 1) {
                        currentIndex++
                    } else {
                        onNextButtonClicked()
                    }
                },
                enabled = isButtonEnabled,
                //버튼 색깔
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFB8500),
                    contentColor = Color.White
                )
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun MessageCard(
    msg: Message,
    viewModel: SurveyViewModel,
    onImageGridDisplayed: (Boolean) -> Unit,
    onResponseReceived: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val selectedImageIndex = remember { mutableStateOf(-1) }
    val selectedIdentityIndex = remember { mutableStateOf(-1) }

    //val imageUrl by viewModel.imageUrl.observeAsState()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = if (msg.author == "User") Arrangement.End else Arrangement.Start) {
        if (msg.author != "User") {
            Image(
                painter = painterResource(R.drawable.star),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    //clip image to be shaped as a circle
                    .clip(CircleShape)
                    //set image border
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = if (msg.author == "User") {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                },
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                when (msg.messageType) {
                    MessageType.IMAGE_GRID -> {
                        onImageGridDisplayed(true)
                        val imageUrls by viewModel.imageUrls.observeAsState(emptyList())
                        Log.d("Image", "Trying to get imageUrl: $imageUrls")

                        if (imageUrls.isNotEmpty()) {
                            ImageGrid(
                                images = imageUrls,
                                onImageSelected = { index ->
                                    selectedImageIndex.value = index
                                    onImageGridDisplayed(true)
                                }
                            )
                        } else {
                            Text("No image available")
                        }
                    }
                    MessageType.TEXT_GRID -> {
                        onImageGridDisplayed(false)
                        TextGrid { index ->
                            selectedIdentityIndex.value = index
                        }

                    }
                    MessageType.TEXT -> {
                        onImageGridDisplayed(false)
                        Text(
                            text = msg.text!!,
                            modifier = Modifier.padding(all = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    MessageType.IMAGE -> {
                        onImageGridDisplayed(false)
                        val scenarioUrl by viewModel.scenarioUrls.observeAsState()
                        val scenario = scenarioUrl?.get(msg.scenario!!)

                        //Log.d("Image", "Trying to get imageUrl: $scenario")
                        if (scenario != null) {
                            Image(
                                painter = rememberAsyncImagePainter(scenario),
                                contentDescription = "Message Image",
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .size(200.dp)
                            )
                        } else {
                            Text("No image available")
                        }
                    }
                }

                //val apiService by lazy { ApiServiceSingleton.apiService }
                val identityService by lazy { IdentityServiceSingleton.identityService }
                LaunchedEffect(selectedImageIndex.value) {
                    if (selectedImageIndex.value != -1) {
                        //val imageId = imageList[selectedImageIndex.value]
                        //val imageName = imageNames[selectedImageIndex.value]
                        Log.d("Network", "selectedImageIndex: $selectedImageIndex")
                        onResponseReceived(true)

                        /*
                        try {
                            Log.d("Network", "Sending request")

                            val response = apiService.sendImageSelection(
                                ImageSelectionData(imageName)
                            )

                            if (response.isSuccessful) {
                                Log.e("MessageCard", "Connection Successful")
                                val responseData = response.body()?: return@LaunchedEffect
                                // Do something with the successful response data (e.g., show a confirmation toast)
                                // 이미지 URL을 저장할 파일 경로 생성
                                Log.d("MessageCard", "Image URL received: $responseData")
                                Toast.makeText(context, "Image Download Successful", Toast.LENGTH_SHORT).show()
                                onResponseReceived(true)
                            } else {
                                // Handle the error response
                                val errorBody = response.errorBody()?.string()
                                Log.e("MessageCard", "Error sending image selection: $errorBody")
                                onResponseReceived(false)
                            }
                            // 서버 응답 처리
                        } catch (e: IOException) {
                            // 네트워크 오류 처리
                            Log.e("NetworkError", "IOException: ${e.message}")
                            onResponseReceived(false)
                        } catch (e: Exception) {
                            // 기타 예외 처리
                            Log.e("NetworkError", "Unknown error: ${e.message}")
                            onResponseReceived(false)
                        }
                         */
                    }
                    selectedImageIndex.value = -1
                }



                //Identity 선택 처리하는 함수
                LaunchedEffect(selectedIdentityIndex.value) {
                    if(selectedIdentityIndex.value != -1) {
                        val identityKeys = identityMap.keys.toList()
                        if (selectedIdentityIndex.value in identityKeys.indices) {
                            val selectedKey = identityKeys[selectedIdentityIndex.value]

                            try {
                                Log.d("Network", "Sending request for key :$selectedKey")
                                val response = identityService.sendIdentitySelection(selectedKey)

                                if (response.isNotEmpty()) {
                                    val identityResponse = response.map { it.identityUrl }
                                    viewModel.setImageUrls(identityResponse)
                                    Log.d("ExampleFunction", "Received: ${identityResponse[0]}")
                                    Toast.makeText(
                                        context,
                                        "Identity Download Successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Log.e("ExampleFunction", "Response list is empty")
                                }
                            } catch (e: IOException) {
                                // 네트워크 오류 처리
                                Log.e("NetworkError", "IOException: ${e.message}")
                                onResponseReceived(false)
                            } catch (e: Exception) {
                                // 기타 예외 처리
                                Log.e("NetworkError", "Unknown error: ${e.message}")
                                onResponseReceived(false)
                            }
                        } else {
                            Log.e("ExampleFunction", "Invalid index: ${selectedIdentityIndex.value}")
                        }
                        selectedIdentityIndex.value = -1
                        /*
                        try {
                            val response = identityService.sendScenarios()
                            if (response.isNotEmpty()) {
                                val scenariosResponse = response.map { it.scenario }
                                viewModel.setScenarioUrls(scenariosResponse)
                                Log.d("ScenarioFunction", "Received: ${scenariosResponse[0]}")
                            } else {
                                Log.e("ScenarioFunction", "Response list is empty")
                            }
                        } catch (e: IOException) {
                            Log.e("NetworkError2", "Unknown error: ${e.message}")
                            onResponseReceived(false)
                        } catch (e: Exception) {
                            Log.e("NetworkError2", "Unknown error: ${e.message}")
                            onResponseReceived(false)
                        }*/
                    }
                }
            }
        }
    }
}

@Composable
fun ImageGrid(columns: Int = 2, onImageSelected: (Int) -> Unit, images: List<String>) {
    Row {
        repeat(columns) { column ->
            Column(Modifier.weight(1f)) {
                images.chunked(columns)[column].forEachIndexed { index, imageUrl ->
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val selectedIndex = column * columns + index
                                Log.d("ImageGrid", "Selected image index: $selectedIndex")
                                onImageSelected(selectedIndex)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun TextGrid(columns: Int = 2, onTextSelected: (Int) -> Unit) {
    val identityValues=  identityMap.values.toList()
    Row {
        repeat(columns) { column ->
            Column(Modifier.weight(1f)) {
                identityValues.chunked(columns)[column].forEachIndexed { index, textId ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val selectedIndex = column * columns + index
                                Log.d("TextGrid", "Selected identity index: $selectedIndex")
                                onTextSelected(selectedIndex)
                            }
                    ) {
                        Text(textId)
                    }
                }
            }
        }
    }
}

//상단바 추가
@Composable
fun SurveyTopBar(
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