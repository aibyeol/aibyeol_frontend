/**
 * SampleData for Jetpack Compose Tutorial 
 */
package com.example.sunflower.data.repository

import com.example.sunflower.R
import com.example.sunflower.ui.screen.Message
import com.example.sunflower.ui.screen.MessageType

val imageList = listOf(
    R.drawable.character01,
    R.drawable.character02,
    R.drawable.character03,
    R.drawable.character04
)

val identityList = listOf(
    "빵",
    "뽀로로",
    "꿀벌",
    "하츄핑"
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
            "Lexi",
            MessageType.TEXT,
            "Hello AIByeol"
        ),
        Message(
            "아이별",
            MessageType.TEXT_GRID,
            textIds = identityList
        ),
        Message(
            "아이별",
            MessageType.IMAGE
        ),
        Message(
            "Lexi",
            MessageType.IMAGE_GRID,
            imageIds = imageList
        ),
        Message(
            "User",
            MessageType.TEXT,
            "나는 뽀로로를 좋아해."
        )
    )
}
