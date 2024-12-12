package com.example.hashscope.model

data class ScrapeXRequest(
    val category_id : Int,
    val filename :  String,
    val limit : Int
)