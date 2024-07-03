package com.example.nycschool.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nycschool.data.repository.HighSchoolModel
import com.example.nycschool.data.repository.SatResultModel
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface ApiService {
    @GET("s3k6-pzi2.json")
   // suspend fun getHighSchoolResult(): List<HighSchoolModel>
    suspend fun getHighSchoolResult(@Query("limit") limit: Int, @Query("offset") offset: Int): List<HighSchoolModel>

    @GET("f9bf-2cp4.json")
    suspend fun getSatResult(): List<SatResultModel>
}