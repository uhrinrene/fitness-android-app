package com.project.fitify

interface ExerciseListContract {

    data class State(
        val state: StatefulModel<ExercisePacksUiModel>
    ) : UiState

    sealed interface Effect : UiEffect {

        data class OpenDetailScreen(val packCode: String, val exerciseCode: String) : Effect
    }

    sealed interface Event : UiEvent {
        data class OnExerciseClicked(val packCode: String, val exerciseCode: String) : Event
        data class OnValueChanged(val query: String) : Event
    }

}