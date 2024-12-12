package com.example.hashscope.model

data class MainTopic(
    val topic_id: Int,
    val topics_name: String,
    val frequency: Int,
    val x_datasets_idx_datasets: Int?,
    val web_datasets_idweb_datasets: Int?,
    val youtube_datasets_idyoutube_datasets: Int?
)
