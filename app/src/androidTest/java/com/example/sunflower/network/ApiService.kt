package com.example.sunflower.network

import android.graphics.BitmapFactory
import android.widget.ImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

private const val BASE_URL = "http://43.200.220.207:8080"//스프링서버
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())//Json변환할때 필요
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService{
    @GET("user/home")
    fun getHome(): Call<String>//응답타입 string

    @POST("user/save")
    fun saveUser(@Body user:User): Call<User>

    @POST("user/character")
    fun saveCharacter(@Body char:Character): Call<Character>

    @GET("scenario")
    fun getScenario(@Url FileUrl: String): Call<ResponseBody>

    @GET("reward")
    fun getReward(): Call<String>
}

//인스턴스 생성
private val apiService = retrofit.create(ApiService::class.java)

//test
fun HomeData(){
    val call = apiService.getHome()

    call.enqueue(object : Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if(response.isSuccessful){
                val responseData = response.body()
                println("Response data: $responseData")
            }
            else
            {
                println("Request Failed: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Network call failed: ${t.message}")
        }
    })
}

//캐릭터 선택(POST)
fun saveCharacter(){
    val newCharacter = Character(character = "pororo")

    val call = apiService.saveCharacter(newCharacter)

    call.enqueue(object : Callback<Character>
    {
        override fun onResponse(call: Call<Character>, response: Response<Character>) {
            if (response.isSuccessful) {
                val responseData = response.body()
                println("User created: $responseData")
            } else {
                println("Request Failed: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<Character>, t: Throwable) {
            println("Network call failed: ${t.message}")
        }
    })

}
//유저정보 넘기기(POST)
fun saveUser(){
    val newUser = User(name = "minji", age = 10, gender = "female", level = 2, prefer = "pororo")

    val call = apiService.saveUser(newUser)

    call.enqueue(object : Callback<User>
    {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                val responseData = response.body()
                println("User created: $responseData")
            } else {
                println("Request Failed: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<User>, t: Throwable) {
            println("Network call failed: ${t.message}")
        }
    })

}

//시나리오 받기(GET)
fun scenarioData(imageView: ImageView, imageUrl: String){
    val call = apiService.getScenario(imageUrl)

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                response.body()?.byteStream()?.let { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView.setImageBitmap(bitmap)
                }
            } else {
                println("Failed to download image: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Network request failed: ${t.message}")
        }
    })
}

//반응 리턴(GET)
fun RewardData(){
    val call = apiService.getReward()

    call.enqueue(object : Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if(response.isSuccessful){
                val responseData = response.body()
                println("Response data: $responseData")
            }
            else
            {
                println("Request Failed: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Network call failed: ${t.message}")
        }
    })
}

//표정 이미지 전송

//음성 데이터 전송

fun main(){

}