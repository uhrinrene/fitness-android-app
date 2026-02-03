package com.project.fitify.contract.list

import com.project.fitify.StatefulUiModel
import com.project.fitify.common.mvi.UiEffect
import com.project.fitify.common.mvi.UiEvent
import com.project.fitify.common.mvi.UiState
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper

interface ListContract {

    data class State(
        val state: StatefulUiModel<ListUiMapper.ContentUiModel>
    ) : UiState

    sealed interface Effect : UiEffect {

        data class OpenDetailScreen(val packCode: String, val exerciseCode: String) : Effect
    }

    sealed interface Event : UiEvent {
        data class OnExerciseClicked(val packCode: String, val exerciseCode: String) : Event
        data class Search(val query: String) : Event
        data object SearchRetry : Event
        data object LoadList : Event
    }

}