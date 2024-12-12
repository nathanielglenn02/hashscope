package com.example.hashscope.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.databinding.FragmentDetailWebBinding
import com.example.hashscope.model.PlatformData

class DetailWebFragment : Fragment() {

    private var _binding: FragmentDetailWebBinding? = null
    private val binding get() = _binding!!

    private lateinit var platformData: PlatformData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            platformData = it.getSerializable("DATA") as PlatformData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvFullText.text = platformData.full_text
        binding.tvSource.text = "Source: ${platformData.source ?: "N/A"}"
        binding.tvUrl.text = "URL: ${platformData.url ?: "N/A"}"
        binding.tvCreatedAt.text = "Created At: ${platformData.created_at ?: "N/A"}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(data: PlatformData): DetailWebFragment {
            val fragment = DetailWebFragment()
            val args = Bundle()
            args.putSerializable("DATA", data)
            fragment.arguments = args
            return fragment
        }
    }
}
