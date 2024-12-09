package com.example.hashscope.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.adapter.MainTopicAdapter
import com.example.hashscope.databinding.FragmentMainBinding
import com.example.hashscope.model.MainTopic
import com.example.hashscope.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainTopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainTopicAdapter(emptyList()) // Initialize the adapter with an empty list
        binding.recyclerView.adapter = adapter

        // Mendapatkan ID kategori yang dikirim melalui argument bundle
        val categoryId = arguments?.getInt("CATEGORY_ID") ?: 0 // Default value is 0
        fetchMainTopics(categoryId)
    }

    // Fungsi untuk mengambil dan mengurutkan MainTopics berdasarkan frekuensi
    private fun fetchMainTopics(categoryId: Int) {
        lifecycleScope.launch {
            try {
                // Memanggil API menggunakan suspend function
                val response: Response<List<MainTopic>> = RetrofitClient.apiService.getMainTopics(categoryId)

                // Debugging: Log request URL dan status code
                Log.d("MainFragment", "API Request URL: ${RetrofitClient.apiService.getMainTopics(categoryId)}")
                Log.d("MainFragment", "Response Status: ${response.code()}")

                if (response.isSuccessful) {
                    val mainTopics = response.body() ?: emptyList()

                    // Cek apakah data mainTopics ada atau kosong
                    if (mainTopics.isEmpty()) {
                        binding.recyclerView.visibility = View.GONE
                        binding.noMainTopicsTextView.visibility = View.VISIBLE
                    } else {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.noMainTopicsTextView.visibility = View.GONE
                        val sortedTopics = mainTopics.sortedByDescending { it.frequency }
                        displayMainTopics(sortedTopics)
                    }
                } else {
                    Log.e("MainFragment", "Error: ${response.message()}")
                }

            } catch (e: Exception) {
                // Tangani error jika API gagal
                Log.e("MainFragment", "Exception: ${e.message}")
            }
        }
    }

    // Fungsi untuk menampilkan main topics pada RecyclerView
    private fun displayMainTopics(mainTopics: List<MainTopic>) {
        adapter.updateData(mainTopics)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
