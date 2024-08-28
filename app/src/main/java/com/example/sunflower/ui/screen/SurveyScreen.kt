package com.example.sunflower.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sunflower.data.ApiService
import com.example.sunflower.data.ImageSelectionData
import com.example.sunflower.R
import com.example.sunflower.data.SampleData
import com.example.sunflower.data.imageList
import com.example.sunflower.data.imageNames
import com.example.sunflower.ui.theme.SunflowerTheme
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

data class Message(
    val author: String,
    val messageType: MessageType,
    val text: String? = null,
    val imageId: Int? = null,
    val imageIds: List<Int>? = null
)

enum class MessageType {
    TEXT,
    IMAGE,
    IMAGE_GRID
}

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("http://3.35.89.10:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

@Composable
fun SurveyScreen(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    onNextButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { message ->
                MessageCard(message)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onNextButtonClicked
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = if (msg.author == "User") Arrangement.End else Arrangement.Start
    ) {
        if (msg.author != "User") {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
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
                    MessageType.IMAGE_GRID -> ImageGrid { index ->
                        selectedImageIndex.value = index
                    }
                    MessageType.TEXT -> Text(
                        text = msg.text ?: "",
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    MessageType.IMAGE -> Image(
                        painter = painterResource(id = msg.imageId ?: R.drawable.placeholder),
                        contentDescription = "Message Image",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                val apiService = retrofit.create(ApiService::class.java)
                LaunchedEffect(selectedImageIndex.value) {
                    if (selectedImageIndex.value != -1) {
                        val imageName = imageNames[selectedImageIndex.value]
                        Log.d("Network", "selectedImageIndex: ${selectedImageIndex.value}, imageName: $imageName")
                        try {
                            val response = apiService.sendImageSelection(
                                ImageSelectionData(imageName)
                            )

                            if (response.isSuccessful) {
                                Log.e("MessageCard", "Connection Successful")
                                val responseData = response.body() ?: return@LaunchedEffect
                                Log.d("MessageCard", "Image URL received: $responseData")
                                Toast.makeText(context, "Image Download Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Log.e("MessageCard", "Error sending image selection: $errorBody")
                            }
                        } catch (e: IOException) {
                            Log.e("NetworkError", "IOException: ${e.message}")
                        } catch (e: Exception) {
                            Log.e("NetworkError", "Unknown error: ${e.message}")
                        }
                    }
                }
            }
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
        SurveyScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            messages = SampleData.conversationSample,
            onNextButtonClicked = {}
        )
    }
}
