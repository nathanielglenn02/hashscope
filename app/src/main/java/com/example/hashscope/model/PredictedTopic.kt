package com.example.hashscope.model

data class PredictedTopic(
    val step: Int,
    val top_topic: String,
    val previous_frequency: Float,
    val predicted_frequency: Float,
    val change_percentage: Float
)
