package com.example.nycschool.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschool.data.model.SatScore
import com.example.nycschool.data.model.School
import com.example.nycschool.data.repository.SchoolRepository
import com.example.nycschool.domain.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {
    val networkStatus = mutableStateOf<NetworkStatus<List<School>>>(NetworkStatus.Loading)
    private val _schools = MutableStateFlow<List<School>>(emptyList())
    val schools: StateFlow<List<School>> = _schools
    private val errorMessage = mutableStateOf("")

    private val _satScores = MutableStateFlow<Map<String, SatScore>>(emptyMap())
    val satScores: StateFlow<Map<String, SatScore>> = _satScores
    init {
        loadSchoolData()
    }
    fun loadSchoolData() {
        try {
            networkStatus.value = NetworkStatus.Loading
            viewModelScope.launch(Dispatchers.IO) {
                val schools = repository.getSchools()
                val satScores = repository.getSatScores().associateBy { it.dbn }
                withContext(Dispatchers.Main) {
                    _schools.value = schools
                    networkStatus.value = NetworkStatus.Success(schools)
                    //_satScores.value = satScores
                }
            }
        } catch (e: Exception) {
            errorMessage.value = e.message.toString()
            networkStatus.value = NetworkStatus.Error(e.message.toString())
        }
    }

    /**
     * Gets the SAT score for a specific school.
     *
     * @param dbn The DBN (District Borough Number) of the school.
     * @return The SatScore object for the school, or null if not found.
     */
    fun getSatScoreForSchool(dbn: String): SatScore? {
        return _satScores.value[dbn]
    }
}