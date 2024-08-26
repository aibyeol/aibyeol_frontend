package com.example.sunflower.data.api

import com.example.sunflower.data.repository.ImageSelectionData
import com.example.sunflower.data.repository.ImageSelectionResponse
import com.example.sunflower.data.repository.UploadAudioRequest
import okhttp3.MultipartBody
import retrofit2.http.*
import retrofit2.Response
import retrofit2.http.Headers

interface ApiService {
    @POST("user/select")
    @Headers("accept: application/json", "content-Type: application/json")
    suspend fun sendImageSelection(@Body data: ImageSelectionData): Response<ImageSelectionResponse>

    @Multipart
    @POST("evaluation/speech")
    suspend fun uploadAudio(@Part audioFile: MultipartBody.Part): Response<UploadAudioRequest>
}