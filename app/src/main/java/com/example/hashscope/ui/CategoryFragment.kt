package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.R
import com.example.hashscope.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup category click listeners
        binding.technologyCategory.setOnClickListener {
            // Navigate to Technology screen or perform any action
        }
        binding.economyCategory.setOnClickListener {
            // Navigate to Economy screen or perform any action
        }
        binding.politicsCategory.setOnClickListener {
            // Navigate to Politics screen or perform any action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
