package com.example.hashscope.network

import com.example.hashscope.auth.LoginRequest
import com.example.hashscope.auth.LoginResponse
import com.example.hashscope.auth.RegisterRequest
import com.example.hashscope.auth.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register") // Sesuaikan dengan endpoint yang ada di backend Flask Anda
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}
