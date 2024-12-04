package com.example.hashscope.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hashscope.databinding.FragmentCategoryBinding
import com.example.hashscope.ui.HomeActivity

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.technologyCategory.setOnClickListener {
            navigateToHomeActivity("Technology")
        }
        binding.economyCategory.setOnClickListener {
            navigateToHomeActivity("Economy")
        }
        binding.politicsCategory.setOnClickListener {
            navigateToHomeActivity("Politics")
        }
    }

    private fun navigateToHomeActivity(category: String) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.putExtra("CATEGORY", category) // Pass the selected category
        startActivity(intent)
        requireActivity().finish() // Optional: close CategoryFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
