package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hashscope.R
import com.example.hashscope.custom.CustomMarkerView
import com.example.hashscope.custom.CustomMarkerView2
import com.example.hashscope.databinding.FragmentGrafikBinding
import com.example.hashscope.model.MainTopic
import com.example.hashscope.model.PredictedTopic
import com.example.hashscope.network.RetrofitClient
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

class GrafikFragment : Fragment() {

    private var _binding: FragmentGrafikBinding? = null
    private val binding get() = _binding!!
    private var mainTopics: List<MainTopic> = emptyList()
    private var predictedTopics: List<PredictedTopic> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrafikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCategoryId = arguments?.getInt("CATEGORY_ID") ?: 0

        // Tampilkan loading untuk kedua chart
        showLoadingBarChart()
        showLoadingLineChart()

        fetchMainTopics(selectedCategoryId)
        extractTopics()

        binding.btnDetailBarChart.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("DATA_LIST", ArrayList(mainTopics)) // Kirim data MainTopic
                putString("CHART_TYPE", "BAR_CHART")
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DetailGrafikFragment::class.java, bundle)
                .addToBackStack(null)
                .commit()
        }

        binding.btnDetailLineChart.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("DATA_LIST", ArrayList(predictedTopics)) // Kirim data PredictedTopic
                putString("CHART_TYPE", "LINE_CHART")
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DetailGrafikFragment::class.java, bundle)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun fetchMainTopics(categoryId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getMainTopics(categoryId, page = 1, limit = 10)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        mainTopics = data
                        updateBarChart(data)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                hideLoadingBarChart()
            }
        }
    }

    private fun fetchPredictedTopics() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPredictedTopics()
                if (response.isSuccessful) {
                    val predictedResponse = response.body()
                    if (predictedResponse != null) {
                        predictedTopics = predictedResponse.predictions
                        updateLineChart(predictedResponse.predictions)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                hideLoadingLineChart()
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

    private fun updateLineChart(data: List<PredictedTopic>) {
        if (data.isEmpty()) {
            println("Line Chart: No data to display")
            return
        }

        val entries = data.mapIndexed { index, topic ->
            Entry(index.toFloat(), topic.predicted_frequency)
        }

        val dataSet = LineDataSet(entries, "Prediksi Frekuensi")
        dataSet.color = android.graphics.Color.BLACK
        dataSet.valueTextColor = android.graphics.Color.BLACK
        dataSet.valueTextSize = 10f
        dataSet.lineWidth = 2f

        val lineChart = binding.lineChart
        lineChart.data = LineData(dataSet)

        // Tambahkan MarkerView dengan Binding
        val markerView = CustomMarkerView2(requireContext(), data)
        lineChart.marker = markerView

        // Atur label di X-Axis
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(data.map { it.top_topic })
        lineChart.xAxis.textColor = android.graphics.Color.BLACK
        lineChart.xAxis.labelRotationAngle = -45f
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.axisLeft.textColor = android.graphics.Color.BLACK
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.textColor = android.graphics.Color.BLACK

        lineChart.invalidate() // Refresh grafik
    }

    private fun showLoadingBarChart() {
        binding.loadingBarChart.visibility = View.VISIBLE
        val logo = binding.loadingBarChart.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo.startAnimation(rotateAnimation)
    }

    private fun hideLoadingBarChart() {
        _binding?.let { binding ->
            binding.loadingBarChart.visibility = View.GONE
            val logo = binding.loadingBarChart.findViewById<View>(R.id.loadingLogo)
            logo.clearAnimation() // Hentikan animasi
        }
    }

    private fun showLoadingLineChart() {
        binding.loadingLineChart.visibility = View.VISIBLE
        val logo = binding.loadingLineChart.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo.startAnimation(rotateAnimation)
    }

    private fun hideLoadingLineChart() {
        _binding?.let { binding ->
            binding.loadingLineChart.visibility = View.GONE
            val logo = binding.loadingLineChart.findViewById<View>(R.id.loadingLogo)
            logo.clearAnimation() // Hentikan animasi
        }
    }


    private fun extractTopics() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.extractTopics()
                if (response.isSuccessful) {
                    println("Extract Topics Success: ${response.body()}")
                    fetchPredictedTopics() // Lanjutkan hanya jika sukses
                } else {
                    println("Error during topic extraction: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
