package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.adapter.MainTopicAdapter
import com.example.hashscope.databinding.FragmentMainBinding
import com.example.hashscope.network.MainTopicPagingSource
import com.example.hashscope.network.RetrofitClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        // Set up RecyclerView with LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainTopicAdapter()
        binding.recyclerView.adapter = adapter

        // Setup load state listener for progress bar, errors, and load more
        adapter.addLoadStateListener { loadState ->
            // Show progress bar when loading data
            binding.progressBar.visibility = if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE

            // Handle errors
            if (loadState.source.refresh is LoadState.Error) {
                val error = (loadState.source.refresh as LoadState.Error).error
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = error.message
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.errorText.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }

//            // Handle load more button visibility
//            if (loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
//                binding.loadMoreButton.visibility = View.GONE // Hide Load More button when all data is loaded
//            } else {
//                binding.loadMoreButton.visibility = View.VISIBLE // Show Load More button if more data available
//            }
        }

        // Mendapatkan ID kategori yang dikirim melalui argument bundle
        val categoryId = arguments?.getInt("CATEGORY_ID") ?: 0 // Default value is 0
        fetchMainTopics(categoryId)
    }

    private fun fetchMainTopics(categoryId: Int) {
        lifecycleScope.launch {
            // Paging configuration for 10 items per page
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 10,  // Define the number of items per page
                    enablePlaceholders = false,  // Don't show placeholders for missing data
                    prefetchDistance = 2,  // Fetch additional pages in advance
                ),
                pagingSourceFactory = { MainTopicPagingSource(RetrofitClient.apiService, categoryId) }
            )

            // Collect paging data
            pager.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)  // Submit data to the adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
