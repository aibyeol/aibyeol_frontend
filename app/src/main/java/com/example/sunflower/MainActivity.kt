package com.example.sunflower

import android.os.Bundle
import android.util.Log
import android.graphics.Bitmap
import android.os.Environment

import android.content.Context
import android.content.Intent

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import androidx.core.content.FileProvider
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.transform.CircleCropTransformation
import java.io.File
import java.io.FileOutputStream

import com.example.sunflower.ui.theme.SunflowerTheme
import com.example.sunflower.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import okhttp3.OkHttpClient
import retrofit2.http.POST
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunflowerTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

enum class MessageType {
    TEXT,
    IMAGE,
    IMAGE_GRID
}

data class Message(
    val author: String,
    val messageType: MessageType,
    val text: String? = null,
    val imageId: Int? = null,
    val imageIds: List<Int>? = null
)
/*
object ApiService {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-api-base-url")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
*/
val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS) // Set connection timeout to 10 seconds (you can adjust this value)
    .readTimeout(20, TimeUnit.SECONDS) // Set read timeout to 20 seconds (you can adjust this value)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("http://3.35.89.10:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

@Composable
fun UrlDisplayScreen(url: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = url)
        // URL을 클릭하면 해당 URL로 이동하도록 설정
        Button(onClick = {
            // URL을 열기 위한 Intent 생성
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }) {
            Text(text = "URL 열기")
        }
    }
}

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Conversation") {
        composable("Conversation") {
             {
                Conversation(SampleData.conversationSample)
            }
        }
        composable("url_display_screen/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            UrlDisplayScreen(url)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    val context = LocalContext.current
    // Add padding around our message
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = if (msg.author == "User") Arrangement.End else Arrangement.Start) {
        if (msg.author != "User") {
            Image(
                painter = painterResource(R.drawable.profile_picture),
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
                val selectedImageIndex = remember { mutableStateOf(-1) }
                when (msg.messageType) {
                    MessageType.IMAGE_GRID -> ImageGrid(){ index ->
                        selectedImageIndex.value = index
                        //Log.d("imagegrid", "imgedid is $index")
                    }
                    MessageType.TEXT -> Text(
                        text = msg.text!!,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    MessageType.IMAGE -> Image(
                        painter = painterResource(id = msg.imageId!!),
                        contentDescription = "Message Image",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                val apiService = retrofit.create(ApiService::class.java)
                LaunchedEffect(selectedImageIndex.value) {
                    if (selectedImageIndex.value != -1) {
                        //val imageId = imageList[selectedImageIndex.value]
                        val imageName = imageNames[selectedImageIndex.value]
                        Log.d("Network", "selectedImageIndex: $selectedImageIndex, imageName: $imageName")
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
                            } else {
                                // Handle the error response
                                val errorBody = response.errorBody()?.string()
                                Log.e("MessageCard", "Error sending image selection: $errorBody")
                            }
                            // 서버 응답 처리
                        } catch (e: IOException) {
                            // 네트워크 오류 처리
                            Log.e("NetworkError", "IOException: ${e.message}")
                        } catch (e: Exception) {
                            // 기타 예외 처리
                            Log.e("NetworkError", "Unknown error: ${e.message}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Composable
fun ImageGrid(columns: Int = 2, onImageSelected: (Int) -> Unit) {
    Row {
        repeat(columns) { column ->
            Column(Modifier.weight(1f)) {
                imageList.chunked(columns)[column].forEachIndexed { index, imageId ->
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
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

@Preview
@Composable
fun PreviewConversation() {
    SunflowerTheme {
        Conversation(SampleData.conversationSample)
    }
}