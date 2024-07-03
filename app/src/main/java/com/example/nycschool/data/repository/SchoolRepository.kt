package com.example.nycschool.data.repository

import com.example.nycschool.data.model.SatScore
import com.example.nycschool.data.model.School
import com.example.nycschool.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val api: ApiService) {
    /**
     * Fetches the list of NYC high schools from the API.
     *
     * @return A list of School objects.
     */
    suspend fun getSchools(): List<School> = withContext(Dispatchers.IO) {
        api.getSchools()
    }

    /**
     * Fetches the SAT scores for NYC high schools from the API.
     *
     * @return A list of SatScore objects.
     */
    suspend fun getSatScores(): List<SatScore> = withContext(Dispatchers.IO) {
        api.getSatScore()
    }
}