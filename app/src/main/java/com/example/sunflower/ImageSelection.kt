package com.example.sunflower
import com.google.gson.annotations.SerializedName

data class ImageSelectionData (
    val character: String
)

data class ImageSelectionResponse(
    @SerializedName("scenario")
    val scenario: String
)