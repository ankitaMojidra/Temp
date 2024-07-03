package com.example.nycschool.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.data.model.SatScore
import com.example.nycschool.data.remote.ApiService
import com.example.nycschool.domain.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatScoreViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val networkStatus = mutableStateOf<NetworkStatus<List<SatScore>>>(NetworkStatus.Loading)
    val allSatResultUsers = mutableStateOf<List<SatScore>>(emptyList())
    val errorMessage = mutableStateOf("")

    init {
        getSatResultData()
    }

    private fun getSatResultData() {
        viewModelScope.launch {
            networkStatus.value = NetworkStatus.Loading
            try {
                val result = apiService.getSatScore()
                allSatResultUsers.value = result
                networkStatus.value = NetworkStatus.Success(result)
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
                networkStatus.value = NetworkStatus.Error(e.message.toString())
            }
        }
    }
}