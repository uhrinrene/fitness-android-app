package com.project.fitify.contract.detail

import com.project.fitify.StatefulUiModel
import com.project.fitify.common.mvi.UiEffect
import com.project.fitify.common.mvi.UiEvent
import com.project.fitify.common.mvi.UiState
import com.project.fitify.viewmodel.detail.uimapping.DetailUiMapper

interface DetailContract {

    data class State(
        val state: StatefulUiModel<DetailUiMapper.ContentUiModel>
    ) : UiState

    sealed interface Effect : UiEffect

    sealed interface Event : UiEvent {
        data object LoadExercise : Event
    }

}