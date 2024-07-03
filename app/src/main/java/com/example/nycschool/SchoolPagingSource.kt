package com.example.nycschool

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nycschool.data.api.ApiService
import com.example.nycschool.data.repository.HighSchoolModel
import retrofit2.HttpException
import java.io.IOException

class HighSchoolPagingSource(private val apiService: ApiService) : PagingSource<Int, HighSchoolModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HighSchoolModel> {
        return try {
            val position = params.key ?: 0
            val response = apiService.getHighSchoolResult(params.loadSize, position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 0) null else position - params.loadSize,
                nextKey = if (response.isEmpty()) null else position + params.loadSize
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HighSchoolModel>): Int? {
        return state.anchorPosition
    }
}
