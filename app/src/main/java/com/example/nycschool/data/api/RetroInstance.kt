package com.example.nycschool.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(70, TimeUnit.SECONDS)  // Increase connection timeout
            .readTimeout(70, TimeUnit.SECONDS)     // Increase read timeout
            .writeTimeout(70, TimeUnit.SECONDS)    // Increase write timeout
            .build()
    }

    /* private val gson: Gson by lazy {
         GsonBuilder()
             .registerTypeAdapter(HighSchoolModel::class.java, HighSchoolModelAdapter())
             .create()
     }*/

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}