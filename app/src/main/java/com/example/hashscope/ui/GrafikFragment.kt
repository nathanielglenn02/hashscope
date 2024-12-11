package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hashscope.R
import com.example.hashscope.custom.CustomMarkerView
import com.example.hashscope.databinding.FragmentGrafikBinding
import com.example.hashscope.model.MainTopic
import com.example.hashscope.network.RetrofitClient
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

class GrafikFragment : Fragment() {

    private var _binding: FragmentGrafikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrafikBinding.inflate(inflater, container, false)
        val view = binding.root

        // Mendapatkan ID kategori yang dikirim melalui argument bundle
        val selectedCategoryId = arguments?.getInt("CATEGORY_ID") ?: 0 // Default value is 0
        fetchMainTopics(selectedCategoryId) // Panggil API untuk grafik pertama

        return view
    }

    private fun fetchMainTopics(categoryId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getMainTopics(categoryId, page = 1, limit = 10)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        updateBarChart(data) // Perbarui grafik dengan data
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateBarChart(data: List<MainTopic>) {
        val entries = data.mapIndexed { index, topic ->
            BarEntry(index.toFloat(), topic.frequency.toFloat())
        }

        val labels = data.map { it.topics_name }

        val dataSet = BarDataSet(entries, "Frekuensi Topik Saat Ini")
        dataSet.colors = listOf(
            android.graphics.Color.parseColor("#90CAF9"),
            android.graphics.Color.parseColor("#B0BEC5"),
            android.graphics.Color.parseColor("#EF9A9A")
        )
        dataSet.valueTextColor = android.graphics.Color.WHITE
        dataSet.valueTextSize = 12f

        val barChart = binding.barChart
        barChart.data = BarData(dataSet)

        // Atur label di X-Axis
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.textColor = android.graphics.Color.WHITE
        barChart.xAxis.labelRotationAngle = -45f
        barChart.xAxis.granularity = 1f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        barChart.axisLeft.textColor = android.graphics.Color.WHITE
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.legend.textColor = android.graphics.Color.WHITE

        // Tambahkan MarkerView
        val markerView = CustomMarkerView(requireContext(), R.layout.marker_view, labels)
        barChart.marker = markerView

        barChart.invalidate() // Refresh grafik
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBarChart() {
        val barChart = binding.barChart
        barChart.data = BarData() // Placeholder untuk data
        barChart.invalidate()
    }

    private fun setupLineChart() {
        val lineChart = binding.lineChart
        lineChart.data = LineData() // Placeholder untuk data
        lineChart.invalidate()
    }
}
