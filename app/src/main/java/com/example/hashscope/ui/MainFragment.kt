package com.example.hashscope.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashscope.R
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
    private var loadingView: View? = null // Loading view container
    private var paginationLoadingView: View? = null // Loading for pagination

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan indikator loading
        addLoadingIndicator()
        addPaginationLoadingIndicator()

        // Set up RecyclerView with LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainTopicAdapter()
        binding.recyclerView.adapter = adapter

        // Setup load state listener for progress bar and errors
        adapter.addLoadStateListener { loadState ->
            // Tampilkan indikator loading untuk halaman pertama
            if (loadState.source.refresh is LoadState.Loading) {
                showLoading()
            } else {
                hideLoading()
            }

            // Tampilkan indikator loading untuk halaman berikutnya
            if (loadState.append is LoadState.Loading) {
                showPaginationLoading()
            } else {
                hidePaginationLoading()
            }

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

    // Tambahkan indikator loading untuk halaman pertama
    private fun addLoadingIndicator() {
        val inflater = LayoutInflater.from(requireContext())
        loadingView = inflater.inflate(R.layout.loading_indicator, binding.root, false)
        binding.root.addView(loadingView)
        val logo = loadingView?.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo?.startAnimation(rotateAnimation)
        loadingView?.visibility = View.GONE // Awalnya disembunyikan
    }

    private fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView?.visibility = View.GONE
    }

    // Tambahkan indikator loading untuk pagination
    private fun addPaginationLoadingIndicator() {
        val inflater = LayoutInflater.from(requireContext())
        paginationLoadingView = inflater.inflate(R.layout.loading_indicator, binding.root, false)
        binding.root.addView(paginationLoadingView)
        val logo = paginationLoadingView?.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo?.startAnimation(rotateAnimation)
        paginationLoadingView?.visibility = View.GONE // Awalnya disembunyikan
    }

    private fun showPaginationLoading() {
        paginationLoadingView?.visibility = View.VISIBLE
    }

    private fun hidePaginationLoading() {
        paginationLoadingView?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
