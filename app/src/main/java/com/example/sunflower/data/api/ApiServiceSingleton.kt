package com.example.sunflower.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiServiceSingleton {
    companion object {
        private val retrofit: Retrofit by lazy {
            // ... Retrofit 인스턴스 생성 코드 ...
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Set connection timeout to 10 seconds
                .readTimeout(20, TimeUnit.SECONDS) // Set read timeout to 20 seconds
                .build()

            Retrofit.Builder()
                .baseUrl("http://3.35.89.10:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
    }
}