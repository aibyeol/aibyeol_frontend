package com.example.sunflower.data
import com.google.gson.annotations.SerializedName

data class ImageSelectionData (
    val character: String
)

data class ImageSelectionResponse(
    @SerializedName("scenario")
    val scenario: String
)