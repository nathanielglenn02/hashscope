package com.example.hashscope.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hashscope.R

class AccordionAdapter(
    private val fullText: String,
    private val additionalFields: Map<String, String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isExpanded = false // Accordion state

    companion object {
        private const val TYPE_FULL_TEXT = 0
        private const val TYPE_ADDITIONAL_FIELD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_FULL_TEXT else TYPE_ADDITIONAL_FIELD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_FULL_TEXT) {
            FullTextViewHolder(inflater.inflate(R.layout.item_accordion, parent, false))
        } else {
            AdditionalFieldViewHolder(inflater.inflate(R.layout.item_additional_field, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FullTextViewHolder) {
            holder.bind(fullText, isExpanded) {
                isExpanded = !isExpanded
                notifyDataSetChanged() // Refresh data to show/hide additional fields
            }
        } else if (holder is AdditionalFieldViewHolder) {
            val keys = additionalFields.keys.toList()
            val values = additionalFields.values.toList()
            holder.bind(keys[position - 1], values[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return if (isExpanded) 1 + additionalFields.size else 1 // Show additional fields only if expanded
    }

    // ViewHolder for Full Text with Accordion
    class FullTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val fullTextView: TextView = view.findViewById(R.id.fullTextView)
        private val expandIcon: ImageView = view.findViewById(R.id.expandIcon)

        fun bind(fullText: String, isExpanded: Boolean, onClick: () -> Unit) {
            fullTextView.text = fullText
            expandIcon.setImageResource(if (isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
            expandIcon.setOnClickListener { onClick() }
        }
    }

    // ViewHolder for Additional Fields
    class AdditionalFieldViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val keyView: TextView = view.findViewById(R.id.keyTextView)
        private val valueView: TextView = view.findViewById(R.id.valueTextView)

        fun bind(key: String, value: String) {
            keyView.text = key
            valueView.text = value
        }
    }
}
