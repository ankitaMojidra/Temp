package com.example.nycschool.ui.theme.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.data.api.ApiService
import com.example.nycschool.data.repository.HighSchoolModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }
}
