/**
 * SampleData for Jetpack Compose Tutorial 
 */
package com.example.sunflower

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Lexi",
            "Test...Test...Test...",
            "text"
        ),
        Message(
            "User",
            "나는 뽀로로를 좋아해.",
            "text"
        ),
        Message(
            "Lexi",
            """네가 좋아할 만한 캐릭터를 생성해 봤어.
            |마음에 드니?""".trim(),
            "imageGrid"
        ),

    )
}
