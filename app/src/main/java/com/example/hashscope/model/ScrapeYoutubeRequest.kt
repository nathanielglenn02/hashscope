package com.example.hashscope.model

data class ScrapeYouTubeRequest(
    val search_keyword: String,
    val max_results: Int,
    val max_comments: Int
)
