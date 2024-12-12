package com.example.hashscope.network

import com.example.hashscope.auth.LoginRequest
import com.example.hashscope.auth.LoginResponse
import com.example.hashscope.auth.RegisterRequest
import com.example.hashscope.auth.RegisterResponse
import com.example.hashscope.model.MainTopic
import com.example.hashscope.model.PlatformData
import com.example.hashscope.model.PredictedResponse
import com.example.hashscope.model.ScrapeNewsRequest
import com.example.hashscope.model.ScrapeResponse
import com.example.hashscope.model.ScrapeXRequest
import com.example.hashscope.model.ScrapeYouTubeRequest
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

    @POST("register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @GET("main_topics/{categoryId}")
    suspend fun getMainTopics(
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<MainTopic>>

    @GET("platform_data")
    fun getPlatformData(
        @Query("platform") platform: String,
        @Query("category_id") categoryId: Int,
        @Query("main_topic_id") mainTopicId: Int
    ): Call<List<PlatformData>>

    @POST("scrape_news")
    suspend fun scrapeNews(@Body request: ScrapeNewsRequest): Response<ScrapeResponse>

    @POST("scrape_youtube")
    suspend fun scrapeYouTube(@Body request: ScrapeYouTubeRequest): Response<ScrapeResponse>

    @POST("scrape")
    suspend fun scrapeX(@Body request: ScrapeXRequest): Response<ScrapeResponse>

    @POST("extract_topics")
    suspend fun extractTopics(): Response<Map<String, String>>

    @GET("predict_topics")
    suspend fun getPredictedTopics(): Response<PredictedResponse>
}
