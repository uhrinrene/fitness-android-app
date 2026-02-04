package com.project.fitify.viewmodel.detail.uimapping

import com.project.fitify.R
import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.view.stateful.StatefulUiModel

class DetailUiMapper {

    fun provideContentState(
        domainModel: ExerciseDomainModel,
    ) = StatefulUiModel.Content(
        data = ContentUiModel(
            title = domainModel.title,
            backRowRes = R.drawable.left,
            detail = domainModel.detail
        )
    )

    data class ContentUiModel(
        val title: String,
        val backRowRes: Int,
        val detail: String
    )
}