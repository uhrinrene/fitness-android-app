package com.project.fitify.model.list.domainmapping

import com.project.fitify.common.data.ExerciseDtoModel
import com.project.fitify.getThumbnailUrl

data class ExercisesSummaryDomainModel(val items: List<ExerciseSummaryDomainModel>) {

    data class ExerciseSummaryDomainModel(
        val thumbnailUrl: String,
        val title: String,
        val packCode: String,
        val exerciseCode: String
    )
}

fun ExerciseDtoModel.toExercisesSummaryDomainModel() = ExercisesSummaryDomainModel.ExerciseSummaryDomainModel(
        thumbnailUrl = getThumbnailUrl(
            packCode = packCode,
            exerciseCode = exerciseCode
        ),
        title = title,
        packCode = packCode,
        exerciseCode = exerciseCode
    )