package com.example.hashscope.custom

import android.content.Context
import android.widget.LinearLayout
import com.example.hashscope.R
import com.example.hashscope.databinding.MarkerView2Binding
import com.example.hashscope.model.PredictedTopic
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView2(
    context: Context,
    private val data: List<PredictedTopic>
) : MarkerView(context, R.layout.marker_view2) {

    private val binding: MarkerView2Binding

    init {
        // Inflate layout menggunakan View Binding
        val rootView = findViewById<LinearLayout>(R.id.marker_root) // Sesuaikan ID root layout
        binding = MarkerView2Binding.bind(rootView)
    }

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        entry?.let {
            val index = entry.x.toInt()
            val topic = data.getOrNull(index)

            topic?.let {
                binding.topicText.text = "Topic: ${topic.top_topic}"
                binding.previousFrequencyText.text = "Previous: ${topic.previous_frequency}"
                binding.predictedFrequencyText.text = "Predicted: ${topic.predicted_frequency}"
                binding.changePercentageText.text = "Change: ${topic.change_percentage}%"
            }
        }
        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-width / 2).toFloat(), (-height).toFloat())
    }
}
