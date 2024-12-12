package com.example.hashscope.ui

import android.content.Intent
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
    private var loadingView: View? = null
    private var paginationLoadingView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLoadingIndicator()
        addPaginationLoadingIndicator()
        val categoryId = arguments?.getInt("CATEGORY_ID") ?: 0
        adapter = MainTopicAdapter { mainTopic ->
            // Tangani klik pada item
            val intent = Intent(requireContext(), DetailTopicActivity::class.java).apply {
                putExtra("CATEGORY_ID", categoryId)
                putExtra("MAIN_TOPIC_ID", mainTopic.topic_id)
                putExtra("X_DATASETS_ID", mainTopic.x_datasets_idx_datasets ?: -1)
                putExtra("WEB_DATASETS_ID", mainTopic.web_datasets_idweb_datasets ?: -1)
                putExtra("YOUTUBE_DATASETS_ID", mainTopic.youtube_datasets_idyoutube_datasets ?: -1)
            }
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.Loading) {
                showLoading()
            } else {
                hideLoading()
            }

            if (loadState.append is LoadState.Loading) {
                showPaginationLoading()
            } else {
                hidePaginationLoading()
            }

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
        fetchMainTopics(categoryId)
    }


    private fun fetchMainTopics(categoryId: Int) {
        lifecycleScope.launch {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false,
                    prefetchDistance = 2,
                ),
                pagingSourceFactory = { MainTopicPagingSource(RetrofitClient.apiService, categoryId) }
            )
            pager.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun addLoadingIndicator() {
        val inflater = LayoutInflater.from(requireContext())
        loadingView = inflater.inflate(R.layout.loading_indicator, binding.root, false)
        binding.root.addView(loadingView)
        val logo = loadingView?.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo?.startAnimation(rotateAnimation)
        loadingView?.visibility = View.GONE
    }

    private fun showLoading() {
        loadingView?.visibility = View.VISIBLE
    }
    private fun hideLoading() {
        loadingView?.visibility = View.GONE
    }
    private fun addPaginationLoadingIndicator() {
        val inflater = LayoutInflater.from(requireContext())
        paginationLoadingView = inflater.inflate(R.layout.loading_indicator, binding.root, false)
        binding.root.addView(paginationLoadingView)
        val logo = paginationLoadingView?.findViewById<View>(R.id.loadingLogo)
        val rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        logo?.startAnimation(rotateAnimation)
        paginationLoadingView?.visibility = View.GONE
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
