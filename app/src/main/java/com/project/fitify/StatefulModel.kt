package com.project.fitify

import androidx.compose.runtime.Immutable

// TODO proc Immutable
@Immutable
sealed class StatefulModel<out T> {

    data class Loading<T>(val data: T? = null) : StatefulModel<T>()
    data class Content<T>(val data: T) : StatefulModel<T>()
    data class Error(val errorModel: ErrorModel) : StatefulModel<Nothing>()
}

data class ErrorModel(
    val title: String,
    val message: String? = null,
    val button: String,
)