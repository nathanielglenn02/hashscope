package com.example.hashscope.custom

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.example.hashscope.R

class CustomMarkerView(context: Context, layoutResource: Int, private val labels: List<String>) :
    MarkerView(context, layoutResource) {

    private val textView: TextView = findViewById(R.id.tvContent)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val index = e?.x?.toInt() ?: 0
        if (index < labels.size) {
            val topic = labels[index]
            val frequency = e?.y?.toInt() ?: 0
            textView.text = "Topik: $topic\nFrekuensi: $frequency"
        }
        super.refreshContent(e, highlight)
    }

    override fun getX(): Float {
        return -(width / 2).toFloat()
    }

    override fun getY(): Float {
        return -height.toFloat()
    }
}
