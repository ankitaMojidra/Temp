package com.example.nycschool.domain

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T) : NetworkStatus<T>()
    data class Error(val exception: String) : NetworkStatus<Nothing>()
    data object Loading : NetworkStatus<Nothing>()
}