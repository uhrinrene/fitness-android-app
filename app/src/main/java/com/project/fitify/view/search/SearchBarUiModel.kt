package com.project.fitify.view.search

data class SearchBarUiModel(
    val value: String,
    val hint: Int,
    val leadingIcon: Int,
    val onValueChange: (String) -> Unit
)