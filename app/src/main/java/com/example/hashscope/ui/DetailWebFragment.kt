package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.adapter.AccordionAdapter
import com.example.hashscope.databinding.FragmentDetailWebBinding

class DetailWebFragment : Fragment() {

    private var _binding: FragmentDetailWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullText = "Generative AI memimpin tren di platform Web"
        val additionalFields = mapOf(
            "Source" to "TechCrunch",
            "Date Published" to "28/11/2024",
            "Category" to "Technology"
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AccordionAdapter(fullText, additionalFields)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
