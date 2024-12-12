package com.example.hashscope.ui

import NowTopicAdapter
import PredictedTopicAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.databinding.FragmentDetailGrafikBinding
import com.example.hashscope.model.MainTopic
import com.example.hashscope.model.PredictedTopic

class DetailGrafikFragment : Fragment() {

    private var _binding: FragmentDetailGrafikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailGrafikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartType = arguments?.getString("CHART_TYPE")
        val dataList = arguments?.getSerializable("DATA_LIST") as? ArrayList<*>

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        when (chartType) {
            "BAR_CHART" -> {
                val mainTopics = dataList?.filterIsInstance<MainTopic>() ?: listOf()
                binding.recyclerView.adapter = NowTopicAdapter(mainTopics)
            }
            "LINE_CHART" -> {
                val predictedTopics = dataList?.filterIsInstance<PredictedTopic>() ?: listOf()
                binding.recyclerView.adapter = PredictedTopicAdapter(predictedTopics)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
