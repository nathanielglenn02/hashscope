package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.databinding.FragmentDetailXBinding
import com.example.hashscope.model.PlatformData

class DetailXFragment : Fragment() {

    private var _binding: FragmentDetailXBinding? = null
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
        _binding = FragmentDetailXBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tampilkan data di UI menggunakan binding
        binding.tvFullText.text = platformData.full_text
        binding.tvUsername.text = "Username: ${platformData.username ?: "N/A"}"
        binding.tvLocation.text = "Location: ${platformData.location ?: "N/A"}"
        binding.tvRetweetCount.text = "Retweet Count: ${platformData.retweet_count ?: 0}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(data: PlatformData): DetailXFragment {
            val fragment = DetailXFragment()
            val args = Bundle()
            args.putSerializable("DATA", data)
            fragment.arguments = args
            return fragment
        }
    }
}
