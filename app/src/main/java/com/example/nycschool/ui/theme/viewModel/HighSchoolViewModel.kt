package com.example.nycschool.ui.theme.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nycschool.HighSchoolPagingSource
import com.example.nycschool.data.api.ApiService
import com.example.nycschool.data.repository.HighSchoolModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/*@HiltViewModel
class HighSchoolViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    val allHighSchoolUsers = mutableStateOf<List<HighSchoolModel>>(emptyList())
    val errorMessage = mutableStateOf("")

    init {
        getHighSchoolData()
    }

    fun getHighSchoolData() {
        viewModelScope.launch {
            try {
                val result = apiService.getHighSchoolResult()
                allHighSchoolUsers.value = result // Update to handle a list if needed
            } catch (e: IOException) {
                Log.e("HighSchoolViewModel", "Network Error: ${e.message}", e)
                errorMessage.value = "Network Error: Unable to fetch data. Please check your connection."
            } catch (e: Exception) {
                Log.e("HighSchoolViewModel", "Network Error: ${e.message}", e)
                errorMessage.value = e.message.toString()
            }
        }
    }
}*/

@HiltViewModel
class HighSchoolViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    val allHighSchoolUsers: Flow<PagingData<HighSchoolModel>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { HighSchoolPagingSource(apiService) }
    ).flow.cachedIn(viewModelScope)
}