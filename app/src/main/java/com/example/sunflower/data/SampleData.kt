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
<<<<<<< HEAD
            "Lexi",
            MessageType.TEXT,
            "Hello AIByeol"
        ),
        Message(
            "Lexi",
=======
            "Aibyeol",
            MessageType.TEXT,
            "캐릭터를 선택해주세요"
        ),
        Message(
            "Aibyeol",
>>>>>>> 3d2b7b0 (Feat : 피그마 기준 처음 세 페이지 구현 res/drawable에 잡다한 거 다 넣음)
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
