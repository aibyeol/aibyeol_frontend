package com.example.sunflower.data.api

import com.example.sunflower.data.repository.IdentitySelectionResponse
import com.example.sunflower.data.repository.ScenariosResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface IdentityService {
    @GET("aibyeol/identity/Bread")
    suspend fun sendIdentitySelection(): List<IdentitySelectionResponse>

    @GET("aibyeol/scenario/2")
    suspend fun sendScenarios(): List<ScenariosResponse>
}