package com.example.hashscope.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hashscope.model.MainTopic
import retrofit2.HttpException

class MainTopicPagingSource(
    private val apiService: ApiService,
    private val categoryId: Int
) : PagingSource<Int, MainTopic>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainTopic> {
        val page = params.key ?: 1
        val limit = 10

        return try {
            val response = apiService.getMainTopics(categoryId, page, limit)

            if (response.isSuccessful) {
                val mainTopics = response.body() ?: emptyList()

                LoadResult.Page(
                    data = mainTopics,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (mainTopics.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MainTopic>): Int? {
        return state.anchorPosition
    }
}

