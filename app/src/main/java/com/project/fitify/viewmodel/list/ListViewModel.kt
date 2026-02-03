package com.project.fitify.viewmodel.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.fitify.ErrorModel
import com.project.fitify.contract.list.ListContract
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper
import com.project.fitify.model.list.ListActions
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.common.IInteractor
import com.project.fitify.common.mvi.MviViewModel
import com.project.fitify.ResultState
import com.project.fitify.model.search.SearchActions
import com.project.fitify.StatefulModel
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class ListViewModel(
    private val exerciseListInteractor: IInteractor<ListActions, ExercisesSummaryDomainModel>,
    private val listUiMapper: ListUiMapper,
    private val searchInteractor: IInteractor<SearchActions, ExercisesSummaryDomainModel>
) : MviViewModel<ListContract.Event, ListContract.State, ListContract.Effect>() {

    override fun createInitialState() = ListContract.State(state = StatefulModel.Loading())

    init {
        exerciseListCollect()
        // TODO mozna vlastni metoda?
        viewModelScope.launch { exerciseListInteractor.sendAction(action = ListActions.LoadData) }
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

                is ResultState.Success<ExercisesSummaryDomainModel> -> setState {
                    copy(
                        state = listUiMapper.provideContentState(
                            domainModel = resultState.value,
                            uiEvent = ::setEvent
                        )
                    )
                }
            }
        }
    }

    override fun handleEvent(event: ListContract.Event) {
        when (event) {
            is ListContract.Event.OnExerciseClicked -> setEffect { ListContract.Effect.OpenDetailScreen(packCode = event.packCode, exerciseCode = event.exerciseCode) }
            is ListContract.Event.OnValueChanged -> viewModelScope.launch {
                searchInteractor.sendAction(
                    action = SearchActions.Search(
                        query = event.query
                    )
                )
            }
        }
    }
}