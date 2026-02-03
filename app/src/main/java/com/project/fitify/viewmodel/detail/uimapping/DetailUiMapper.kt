package com.project.fitify.viewmodel.detail.uimapping

import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.StatefulModel

class DetailUiMapper {

    fun provideContentState(
        domainModel: ExerciseDomainModel,
    ) = StatefulModel.Content(
        data = ContentUiModel(
            title = domainModel.title,
            detail = domainModel.detail
        )
    )

    data class ContentUiModel(
        val title: String,
        val detail: String
    )
}