package com.example.hashscope.model

import java.io.Serializable

data class PlatformData(
    val idx_datasets: Int?,
    val idweb_datasets: Int?,
    val idyoutube_datasets: Int?,
    val full_text: String,
    val username: String?,
    val location: String?,
    val created_at: String,
    val favorite_count: Int?,
    val quote_count: Int?,
    val reply_count: Int?,
    val retweet_count: Int?,
    val like_count: Int?,
    val source: String?,
    val url: String?,
    val author_name: String?
) : Serializable
