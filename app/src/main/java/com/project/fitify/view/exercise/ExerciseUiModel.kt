package com.project.fitify.view.exercise

data class ExerciseUiModel(
    val id: Int,
    val icon: String,
    val title: String,
    val onClick: () -> Unit
)