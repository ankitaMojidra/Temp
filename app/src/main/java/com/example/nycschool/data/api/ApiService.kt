package com.example.nycschool.data.api

import com.example.nycschool.data.repository.HighSchoolModel
import com.example.nycschool.data.repository.SatResultModel
import retrofit2.http.GET

interface ApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getHighSchoolResult(): List<HighSchoolModel>

    @GET("f9bf-2cp4.json")
    suspend fun getSatResult(): List<SatResultModel>
}