package com.project.fitify

interface ExerciseDetailContract {

    data class State(
        val state: StatefulModel<ExerciseUiModel>
    ) : UiState

    sealed interface Effect : UiEffect

    sealed interface Event : UiEvent

}