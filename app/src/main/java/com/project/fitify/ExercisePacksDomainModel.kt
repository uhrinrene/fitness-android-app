package com.project.fitify

data class ExercisePacksDomainModel(val items: List<ExercisePackDomainModel>) {
    data class ExercisePackDomainModel(
        val thumbnailUrl: String,
        val title: String,
        val packCode: String,
        val exerciseCode: String
    )
}