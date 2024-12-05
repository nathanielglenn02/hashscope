package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.adapter.AccordionAdapter
import com.example.hashscope.databinding.FragmentDetailXBinding

class DetailXFragment : Fragment() {

    private var _binding: FragmentDetailXBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailXBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example data
        val fullText = "Generative AI mencapai masa jayanya"
        val additionalFields = mapOf(
            "Favorite Count" to "120",
            "Quote Count" to "50",
            "Reply Count" to "30",
            "Retweet Count" to "200"
        )

        // Set up RecyclerView
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
