package com.example.sunflower
import com.google.gson.annotations.SerializedName

data class ImageSelectionData (
    val imageName: String
)

data class ImageSelectionResponse(
    @SerializedName("character")
    val character: String
)