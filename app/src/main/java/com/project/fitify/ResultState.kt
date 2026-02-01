package com.project.fitify

sealed class ResultState<out T> {
    data class Success<out T : Any>(val value: T) : ResultState<T>()
    data object Loading : ResultState<Nothing>()
    data class Error(val errorDomainModel: ErrorDomainModel) : ResultState<Nothing>()
}

data class ErrorDomainModel(val throwable: Throwable, val message: String)