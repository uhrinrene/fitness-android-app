package com.project.fitify.view.stateful

import androidx.compose.runtime.Immutable

@Immutable
sealed class StatefulUiModel<out T> {

    data class Loading<T>(val data: T? = null) : StatefulUiModel<T>()
    data class Content<T>(val data: T) : StatefulUiModel<T>()
    data class Error(val errorUiModel: ErrorUiModel) : StatefulUiModel<Nothing>()
}

data class ErrorUiModel(
    val title: String,
    val message: String? = null,
    val button: String,
    val onClick: () -> Unit
)