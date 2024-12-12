package com.example.hashscope.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.databinding.ItemMainTopicBinding
import com.example.hashscope.model.MainTopic

class MainTopicAdapter(
    private val onItemClick: (MainTopic) -> Unit // Callback untuk menangkap event klik
) : PagingDataAdapter<MainTopic, MainTopicAdapter.MainTopicViewHolder>(MainTopicComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTopicViewHolder {
        val binding = ItemMainTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainTopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTopicViewHolder, position: Int) {
        val mainTopic = getItem(position) // Ambil item pada posisi tertentu
        if (mainTopic != null) {
            holder.bind(mainTopic)
        }
    }

    inner class MainTopicViewHolder(private val binding: ItemMainTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mainTopic: MainTopic) {
            // Menampilkan nama topik dan frekuensi
            binding.topicNameTextView.text = mainTopic.topics_name
            binding.topicFrequencyTextView.text = "Frequency: ${mainTopic.frequency}"

            // Tangani klik pada item
            binding.root.setOnClickListener {
                onItemClick(mainTopic) // Panggil callback dengan data MainTopic
            }
        }
    }

    // DiffUtil untuk membandingkan item
    object MainTopicComparator : DiffUtil.ItemCallback<MainTopic>() {
        override fun areItemsTheSame(oldItem: MainTopic, newItem: MainTopic): Boolean {
            return oldItem.topic_id == newItem.topic_id // Bandingkan berdasarkan ID
        }

        override fun areContentsTheSame(oldItem: MainTopic, newItem: MainTopic): Boolean {
            return oldItem == newItem // Bandingkan berdasarkan isi
        }
    }
}
