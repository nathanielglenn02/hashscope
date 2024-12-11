package com.example.hashscope.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.databinding.ItemMainTopicBinding
import com.example.hashscope.model.MainTopic

class MainTopicAdapter :
    PagingDataAdapter<MainTopic, MainTopicAdapter.MainTopicViewHolder>(MainTopicComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTopicViewHolder {
        val binding = ItemMainTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainTopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainTopicViewHolder, position: Int) {
        val mainTopic = getItem(position) // Access the item at position using getItem()
        if (mainTopic != null) {
            holder.bind(mainTopic)
        }
    }

    inner class MainTopicViewHolder(private val binding: ItemMainTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mainTopic: MainTopic) {
            binding.topicNameTextView.text = mainTopic.topics_name
            binding.topicFrequencyTextView.text = "Frequency: ${mainTopic.frequency}"
        }
    }

    // DiffUtil for comparing MainTopic items
    object MainTopicComparator : DiffUtil.ItemCallback<MainTopic>() {
        override fun areItemsTheSame(oldItem: MainTopic, newItem: MainTopic): Boolean {
            return oldItem.id == newItem.id // Compare based on ID
        }

        override fun areContentsTheSame(oldItem: MainTopic, newItem: MainTopic): Boolean {
            return oldItem == newItem // Compare based on contents
        }
    }
}



