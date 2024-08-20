package com.example.sunflower

data class ImageSelectionData (
    val imageId: Int
)

data class ImageSelectionResponse(
    val success: Boolean,
    val message: String
)