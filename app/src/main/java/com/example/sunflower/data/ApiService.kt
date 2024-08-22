package com.example.sunflower.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Headers

interface ApiService {
    @POST("user/select")
    @Headers("accept: application/json", "content-Type: application/json")
    suspend fun sendImageSelection(@Body data: ImageSelectionData): Response<ImageSelectionResponse>
}
