package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.adapter.AccordionAdapter
import com.example.hashscope.databinding.FragmentDetailYoutubeBinding

class DetailYoutubeFragment : Fragment() {

    private var _binding: FragmentDetailYoutubeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailYoutubeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullText = "Generative AI mencapai masa jayanya di platform Youtube"
        val additionalFields = mapOf(
            "Author Name" to "Glenn",
            "Like Count" to "100",
            "Created At" to "29/11/2024"
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
