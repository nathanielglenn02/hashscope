package com.example.hashscope.model

data class MainTopic(
    val topic_id: Int, // ID unik untuk setiap MainTopic
    val topics_name: String, // Nama topik
    val frequency: Int, // Frekuensi topik muncul
    val x_datasets_idx_datasets: Int?, // ID dataset untuk platform X (nullable)
    val web_datasets_idweb_datasets: Int?, // ID dataset untuk platform Web (nullable)
    val youtube_datasets_idyoutube_datasets: Int? // ID dataset untuk platform YouTube (nullable)
)
