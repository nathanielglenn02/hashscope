package com.example.hashscope.network

import com.example.hashscope.auth.LoginRequest
import com.example.hashscope.auth.LoginResponse
import com.example.hashscope.auth.RegisterRequest
import com.example.hashscope.auth.RegisterResponse
import com.example.hashscope.model.MainTopic
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register") // Sesuaikan dengan endpoint yang ada di backend Flask Anda
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("main_topics/{categoryId}")
    suspend fun getMainTopics(@Path("categoryId") categoryId: Int): Response<List<MainTopic>>

}
