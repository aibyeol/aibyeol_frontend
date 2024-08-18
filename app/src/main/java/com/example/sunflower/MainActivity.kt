package com.example.sunflower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.example.sunflower.ui.theme.SunflowerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SunflowerTheme {
                Conversation(SampleData.conversationSample)
                /*
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("Android", "Jetpack Compose"))
                }
                 */
            }
        }
    }
}

data class Message(
    val author: String,
    val body: String,
    val imageResourceId: Int?= null
)

@Composable
fun MessageCard(msg: Message) {
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
        // We keep track if the message is expanded or not in this variable
        /*
        var isExpanded by remember {mutableStateOf(false)}
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
         */
        // We toggle the isExpanded variable when we click on this Column
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
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Column(modifier = Modifier.padding(all = 8.dp)) {
                    if (msg.imageResourceId != null) {
                        //add Image for message with padding
                        Image(
                            painter = painterResource(msg.imageResourceId),
                            contentDescription = "Message Image",
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 4.dp),
                        //maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
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

@Preview
@Composable
fun PreviewConversation() {
    SunflowerTheme {
        Conversation(SampleData.conversationSample)
    }
}
/*
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    SunflowerTheme {
        Surface {
            MessageCard(
                msg = Message("Lexi", "Hey, take a look at Jetpack Compose")
            )
        }
    }
}
 */