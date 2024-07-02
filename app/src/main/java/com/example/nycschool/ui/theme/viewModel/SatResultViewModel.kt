package com.example.nycschool.ui.theme.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.data.api.ApiService
import com.example.nycschool.data.repository.SatResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatResultViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val allSatResultUsers = mutableStateOf<List<SatResultModel>>(emptyList())
    val errorMessage = mutableStateOf("")

    init {
        getSatResultData()
    }

    fun getSatResultData() {
        viewModelScope.launch {
            try {
                val result = apiService.getSatResult()
                allSatResultUsers.value = result
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }
}