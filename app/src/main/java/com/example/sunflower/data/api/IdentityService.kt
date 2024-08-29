package com.example.sunflower.data.api

import com.example.sunflower.data.repository.IdentitySelectionResponse
import com.example.sunflower.data.repository.ScenariosResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IdentityService {
    @GET("aibyeol/identity/{character}")
    suspend fun sendIdentitySelection(@Path("character") character: String): List<IdentitySelectionResponse>

    @GET("aibyeol/scenario/{charId}")
    suspend fun sendCharacterSelection(@Path("charId") charId: Int): List<ScenariosResponse>
}