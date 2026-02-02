package com.project.fitify

class ExerciseDetailUiMapper {

    fun provideContentState(
        domainModel: ExerciseDomainModel,
    ) = StatefulModel.Content(
        data = ExerciseUiModel(
            title = domainModel.title,
            detail = domainModel.detail
        )
    )
}

data class ExerciseUiModel(
    val title: String,
    val detail: String
)