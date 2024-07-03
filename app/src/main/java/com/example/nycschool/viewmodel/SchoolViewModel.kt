package com.example.nycschool.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.data.model.School
import com.example.nycschool.data.remote.ApiService
import com.example.nycschool.domain.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val repository: ApiService
) : ViewModel() {

    val networkStatus = mutableStateOf<NetworkStatus<List<School>>>(NetworkStatus.Loading)
    val schools = MutableStateFlow<List<School>>(emptyList())
    private val errorMessage = mutableStateOf("")

    init {
        loadSchoolData()
    }

    fun loadSchoolData() {
        try {
            networkStatus.value = NetworkStatus.Loading
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getSchools()
                withContext(Dispatchers.Main) {
                    schools.value = result
                    networkStatus.value = NetworkStatus.Success(result)
                }
            }
        } catch (e: Exception) {
            errorMessage.value = e.message.toString()
            networkStatus.value = NetworkStatus.Error(e.message ?: "An unknown error occurred")
        }
    }
}