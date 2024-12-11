package com.example.hashscope.model

data class ScrapeNewsRequest(
    val category: String,
    val filename: String,
    val max_results: Int
)
