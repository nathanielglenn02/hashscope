package com.example.hashscope.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"

    // Konfigurasi OkHttpClient dengan timeout
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS) // Timeout koneksi
            .readTimeout(180, TimeUnit.SECONDS)    // Timeout membaca data
            .writeTimeout(180, TimeUnit.SECONDS)   // Timeout menulis data
            .build()
    }

    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Menggunakan OkHttpClient dengan timeout
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ApiService instance
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
