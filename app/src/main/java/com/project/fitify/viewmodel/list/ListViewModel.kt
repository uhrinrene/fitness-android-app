package com.project.fitify.viewmodel.list

import androidx.lifecycle.viewModelScope
import com.project.fitify.contract.list.ListContract
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper
import com.project.fitify.model.list.ListActions
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.common.IInteractor
import com.project.fitify.common.mvi.MviViewModel
import com.project.fitify.ResultState
import com.project.fitify.model.search.SearchActions
import com.project.fitify.StatefulUiModel
import com.project.fitify.collectEmits
import com.project.fitify.common.uimapper.ErrorUiMapper
import com.project.fitify.contract.list.ListContract.*
import com.project.fitify.model.detail.ExerciseActions
import com.project.fitify.model.search.SearchActions.*
import com.project.fitify.model.search.domainmapping.SearchDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class ListViewModel(
    private val exerciseListInteractor: IInteractor<ListActions, ExercisesSummaryDomainModel>,
    private val listUiMapper: ListUiMapper,
    private val errorUiMapper: ErrorUiMapper,
    private val searchInteractor: IInteractor<SearchActions, SearchDomainModel>
) : MviViewModel<Event, State, Effect>() {

    override fun createInitialState() = State(state = StatefulUiModel.Loading())

    init {
        exerciseListCollect()
        searchCollect()
        setEvent(event = Event.LoadList)
    }

    private fun exerciseListCollect() = viewModelScope.launch {
        exerciseListInteractor.loadData().collectEmits(actionLoading = {
            setState { copy(state = StatefulUiModel.Loading()) }
        }, actionError = { errorDomainModel ->
            setState {
                copy(
                    state = errorUiMapper.provideErrorState(
                        errorDomainModel = errorDomainModel,
                        action = { setEvent(event = Event.LoadList) })
                )
            }
        }, actionSuccess = { result ->
            setState {
                copy(
                    state = listUiMapper.provideContentState(
                        domainModel = result,
                        uiEvent = ::setEvent
                    )
                )
            }
        })
    }

    private fun searchCollect() = viewModelScope.launch {
        searchInteractor.loadData().collectEmits(actionLoading = {
            setState { copy(state = StatefulUiModel.Loading()) }
        }, actionError = { errorDomainModel ->
            setState {
                copy(
                    state = errorUiMapper.provideErrorState(
                        errorDomainModel = errorDomainModel,
                        action = { setEvent(event = Event.SearchRetry) })
                )
            }
        }, actionSuccess = { result ->
            setState {
                copy(
                    state = listUiMapper.provideContentState(
                        domainModel = result.exercisesSummaryDomainModel,
                        query = result.query,
                        uiEvent = ::setEvent
                    )
                )
            }
        })
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnExerciseClicked -> setEffect {
                Effect.OpenDetailScreen(
                    packCode = event.packCode,
                    exerciseCode = event.exerciseCode
                )
            }

            is Event.Search -> viewModelScope.launch {
                searchInteractor.sendAction(
                    action = Search(
                        query = event.query
                    )
                )
            }

            Event.LoadList -> viewModelScope.launch {
                exerciseListInteractor.sendAction(
                    action = ListActions.LoadData
                )
            }

            Event.SearchRetry -> viewModelScope.launch {
                searchInteractor.sendAction(action = Retry)
            }
        }
    }
}