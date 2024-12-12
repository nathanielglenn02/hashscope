package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.databinding.FragmentDetailYoutubeBinding
import com.example.hashscope.model.PlatformData

class DetailYoutubeFragment : Fragment() {

    private var _binding: FragmentDetailYoutubeBinding? = null
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
        _binding = FragmentDetailYoutubeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tampilkan data di UI menggunakan binding
        binding.tvFullText.text = platformData.full_text
        binding.tvAuthorName.text = "Author: ${platformData.author_name ?: "N/A"}"
        binding.tvLikeCount.text = "Like Count: ${platformData.like_count ?: 0}"
        binding.tvCreatedAt.text = "Created At: ${platformData.created_at ?: "N/A"}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(data: PlatformData): DetailYoutubeFragment {
            val fragment = DetailYoutubeFragment()
            val args = Bundle()
            args.putSerializable("DATA", data)
            fragment.arguments = args
            return fragment
        }
    }
}
