/**
 * SampleData for Jetpack Compose Tutorial 
 */
package com.example.sunflower.data

import com.example.sunflower.R
import com.example.sunflower.ui.screen.Message
import com.example.sunflower.ui.screen.MessageType

val imageList = listOf(
    R.drawable.character01,
    R.drawable.character02,
    R.drawable.character03,
    R.drawable.character04
)

val imageNames = listOf(
    "heartsping_default",
    "pinkpong_default",
    "poly_default",
    "pororo_default"
)

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            author = "Lexi",
            messageType = MessageType.TEXT,
            text = "Hello AIByeol"
        ),
        Message(
            author = "Lexi",
            messageType = MessageType.TEXT,
            text = "캐릭터를 선택해주세요"
        ),
        Message(
            author = "Aibyeol",
            messageType = MessageType.IMAGE_GRID,
            imageIds = imageList // IMAGE_GRID에 사용될 이미지 리스트
        ),
        Message(
            author = "User",
            messageType = MessageType.TEXT,
            text = "나는 뽀로로를 좋아해."
        )
    )
}
