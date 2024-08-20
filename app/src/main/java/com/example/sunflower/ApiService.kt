package com.example.sunflower

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("api/images/select")
    suspend fun sendImageSelection(@Body data: ImageSelectionData): Response<ImageSelectionData>
}
