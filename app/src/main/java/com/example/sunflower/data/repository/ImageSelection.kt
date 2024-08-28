package com.example.sunflower.data.repository
import com.google.gson.annotations.SerializedName
import retrofit2.http.Part
import okhttp3.MultipartBody

data class ImageSelectionData (
    val character: String
)

data class ImageSelectionResponse(
    @SerializedName("scenario")
    val scenario: String
)

data class IdentitySelectionResponse(
    val id: String,
    val identity: String,
    val identityUrl: String
)

data class ScenariosResponse(
    val scenarioUrl: String
)

data class UploadAudioRequest(
    @Part("audio")
    val audioFile: MultipartBody.Part
)