package com.example.sunflower.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IdentityServiceSingleton {
    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://43.200.220.207:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        val identityService: IdentityService by lazy { retrofit.create(IdentityService::class.java) }
    }
}