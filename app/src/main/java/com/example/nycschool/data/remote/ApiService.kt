package com.example.nycschool.data.remote

import com.example.nycschool.data.model.School
import com.example.nycschool.data.model.SatScore
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("resource/s3k6-pzi2.json")
    //@GET("jpmc/school/")
    suspend fun getSchools(): List<School>
   // suspend fun getHighSchoolResult(@Query("limit") limit: Int, @Query("offset") offset: Int): List<School>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSatScore(): List<SatScore>
}