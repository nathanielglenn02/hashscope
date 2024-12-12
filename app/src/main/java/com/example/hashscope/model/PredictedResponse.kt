package com.example.hashscope.model

data class PredictedResponse(
    val message: String,
    val predictions: List<PredictedTopic>
)
