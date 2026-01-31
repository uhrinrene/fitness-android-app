package com.project.fitify

sealed class ResultState {
    data class Success<out T : Any>(val value: T) : ResultState()
    data object Loading : ResultState()
    data class Error(val throwable: Throwable) : ResultState()
}