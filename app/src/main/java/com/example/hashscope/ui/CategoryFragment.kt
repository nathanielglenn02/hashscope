package com.example.hashscope.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hashscope.databinding.FragmentCategoryBinding
import com.example.hashscope.model.ScrapeNewsRequest
import com.example.hashscope.model.ScrapeYouTubeRequest
import com.example.hashscope.network.RetrofitClient
import kotlinx.coroutines.launch

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
            navigateToHomeActivity(1) // ID 1 untuk Technology
//            sendNewsRequest("technology", "tech_news.csv", 100)
//            sendYouTubeRequest("technology", 100, 100)
        }
        binding.economyCategory.setOnClickListener {
            navigateToHomeActivity(2) // ID 2 untuk Economy
//            sendNewsRequest("economy", "eco_news.csv", 100)
//            sendYouTubeRequest("economy", 100, 100)
        }
        binding.politicsCategory.setOnClickListener {
            navigateToHomeActivity(3) // ID 3 untuk Politics
//            sendNewsRequest("politics", "poli_news.csv", 100)
//            sendYouTubeRequest("politics", 100, 100)
        }
    }

    // Tambahkan fungsi untuk memanggil endpoint scrape_news
    private fun sendNewsRequest(category: String, filename: String, maxResults: Int) {
        lifecycleScope.launch {
            try {
                val request = ScrapeNewsRequest(category, filename, maxResults)
                val response = RetrofitClient.apiService.scrapeNews(request)

                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to scrape news", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Tambahkan fungsi untuk memanggil endpoint scrape_youtube
    private fun sendYouTubeRequest(searchKeyword: String, maxResults: Int, maxComments: Int) {
        lifecycleScope.launch {
            try {
                val request = ScrapeYouTubeRequest(searchKeyword, maxResults, maxComments)
                val response = RetrofitClient.apiService.scrapeYouTube(request)

                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(requireContext(), response.body()?.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to scrape YouTube data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHomeActivity(categoryId: Int) {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        intent.putExtra("CATEGORY_ID", categoryId) // Pass the category ID
        startActivity(intent)
        requireActivity().finish() // Optional: close CategoryFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
