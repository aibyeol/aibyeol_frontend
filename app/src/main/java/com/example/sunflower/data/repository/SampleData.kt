/**
 * SampleData for Jetpack Compose Tutorial 
 */
package com.example.sunflower.data.repository

import com.example.sunflower.R

val identityMap = mapOf(
    "Bread" to "빵",
    "Princess" to "공주",
    "Cat" to "고양이",
    "Dino" to "공룡"
)

data class Message(
    val author: String,
    val messageType: MessageType,
    val text: String? = null,
    val imageId: Int? = null,
    val scenario: Int? = null,
    val imageIds: List<Int>? = null,
    val textIds: Map<String, String>? = null
)
enum class MessageType {
    TEXT,
    IMAGE,
    IMAGE_GRID,
    TEXT_GRID
}

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "아이별",
            MessageType.TEXT,
            "안녕하세요, 아이별입니다!"
        ),
        Message(
            "아이별",
            MessageType.TEXT_GRID,
            textIds = identityMap
        ),
        Message(
            "아이별",
            MessageType.IMAGE_GRID
        ),
        Message(
            "아이별",
            MessageType.IMAGE,
            scenario = 0
        ),
        Message(
            "User",
            MessageType.IMAGE,
            scenario = 1
        ),
        Message(
            "아이별",
            MessageType.IMAGE,
            scenario = 2
        )
    )
}
