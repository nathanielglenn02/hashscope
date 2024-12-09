package com.example.hashscope.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.R
import com.example.hashscope.model.MainTopic

class MainTopicAdapter(private var mainTopics: List<MainTopic>) : RecyclerView.Adapter<MainTopicAdapter.MainTopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_topic, parent, false)
        return MainTopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainTopicViewHolder, position: Int) {
        val mainTopic = mainTopics[position]
        holder.topicNameTextView.text = mainTopic.topics_name
        holder.topicFrequencyTextView.text = "Frequency: ${mainTopic.frequency}"
    }

    override fun getItemCount(): Int {
        return mainTopics.size
    }

    // Add this method to update data in the adapter
    fun updateData(newData: List<MainTopic>) {
        mainTopics = newData
        notifyDataSetChanged()  // Notify RecyclerView that data has been updated
    }

    inner class MainTopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topicNameTextView: TextView = itemView.findViewById(R.id.topicNameTextView)
        val topicFrequencyTextView: TextView = itemView.findViewById(R.id.topicFrequencyTextView)
    }
}
