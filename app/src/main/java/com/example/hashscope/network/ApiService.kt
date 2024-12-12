package com.example.hashscope.network

import com.example.hashscope.auth.LoginRequest
import com.example.hashscope.auth.LoginResponse
import com.example.hashscope.auth.RegisterRequest
import com.example.hashscope.auth.RegisterResponse
import com.example.hashscope.model.MainTopic
import com.example.hashscope.model.PlatformData
import com.example.hashscope.model.PredictedResponse
import com.example.hashscope.model.PredictedTopic
import com.example.hashscope.model.ScrapeNewsRequest
import com.example.hashscope.model.ScrapeResponse
import com.example.hashscope.model.ScrapeYouTubeRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Login Endpoint
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // Register Endpoint
    @POST("register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    // Get Main Topics
    @GET("main_topics/{categoryId}")
    suspend fun getMainTopics(
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<MainTopic>>

    // Get Platform Data
    @GET("platform_data")
    fun getPlatformData(
        @Query("platform") platform: String,
        @Query("category_id") categoryId: Int,
        @Query("main_topic_id") mainTopicId: Int
    ): Call<List<PlatformData>>

    // Scrape Google News
    @POST("scrape_news")
    suspend fun scrapeNews(@Body request: ScrapeNewsRequest): Response<ScrapeResponse>

    // Scrape YouTube
    @POST("scrape_youtube")
    suspend fun scrapeYouTube(@Body request: ScrapeYouTubeRequest): Response<ScrapeResponse>

    // Extract Topics (New Endpoint)
    @POST("extract_topics")
    suspend fun extractTopics(): Response<Map<String, String>>

    // Predict Topics (New Endpoint)
    @GET("predict_topics")
    suspend fun getPredictedTopics(): Response<PredictedResponse>
}
