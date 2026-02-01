package com.project.fitify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.fitify.ExerciseListContract.*
import com.project.fitify.ExerciseListContract.Event.OnExerciseClicked
import com.project.fitify.ExerciseListContract.Event.OnValueChanged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseListInteractor: IInteractor<ExercisePacksActions, ExercisePacksDomainModel>,
    private val exerciseListUiMapper: ExerciseListUiMapper,
    private val searchInteractor: IInteractor<SearchActions, ExercisePacksDomainModel>
) : MviViewModel<Event, State, Effect>() {

    override fun createInitialState() = State(state = StatefulModel.Loading())

    init {
        exerciseListCollect()
        // TODO mozna vlastni metoda?
        viewModelScope.launch { exerciseListInteractor.sendAction(action = ExercisePacksActions.LoadData) }
    }

    private fun exerciseListCollect() = viewModelScope.launch {
        merge(
            exerciseListInteractor.loadData(),
            searchInteractor.loadData()
        ).collect { resultState ->
            when (resultState) {
                is ResultState.Error -> {
                    setState {
                        copy(
                            state = StatefulModel.Error(
                                errorModel = ErrorModel(
                                    title = "error",
                                    button = "button"
                                )
                            )
                        )
                    }
                    Log.d("TAG", "Error" + resultState.errorDomainModel.message)
                }

                ResultState.Loading -> {
                    setState { copy(state = StatefulModel.Loading()) }
                    Log.d("TAG", "Loading")
                }

                is ResultState.Success<ExercisePacksDomainModel> -> setState {
                    copy(
                        state = exerciseListUiMapper.provideContentState(
                            domainModel = resultState.value,
                            uiEvent = ::setEvent
                        )
                    )
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is OnExerciseClicked -> setEffect { Effect.OpenDetailScreen }
            is OnValueChanged -> viewModelScope.launch {
                searchInteractor.sendAction(
                    action = SearchActions.Search(
                        query = event.query
                    )
                )
            }
        }
    }
}