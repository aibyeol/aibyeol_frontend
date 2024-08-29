package com.example.sunflower.data.repository

class RetrofitRepository private constructor() {
    companion object {
        @Volatile
        private var instance: RetrofitRepository? = null

        fun getInstance(): RetrofitRepository =
            instance ?: synchronized(this) {
                instance ?: RetrofitRepository().also { instance = it }
            }
    }
}