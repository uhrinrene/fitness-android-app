package com.project.fitify.model.search

import com.project.fitify.IExerciseRepository
import com.project.fitify.ResultState
import com.project.fitify.common.IInteractor
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.loadResultState
import com.project.fitify.model.list.domainmapping.toExercisesSummaryDomainModel
import com.project.fitify.model.search.domainmapping.SearchDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchInteractor(private val repository: IExerciseRepository) :
    IInteractor<SearchActions, SearchDomainModel> {

    private val _searchFlow = MutableSharedFlow<SearchActions>()

    override suspend fun sendAction(action: SearchActions) = _searchFlow.emit(value = action)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun loadData(): Flow<ResultState<SearchDomainModel>> {

        lateinit var query: String

        return _searchFlow.debounce(timeoutMillis = DEBOUNCE).flatMapLatest { action ->
            when (action) {
                SearchActions.Retry -> search(query = query)
                is SearchActions.Search -> {
                    query = action.query
                    search(query = query)
                }
            }
        }
    }

    private fun search(query: String): Flow<ResultState<SearchDomainModel>> = loadResultState {
        val exercises = repository.searchExercises(query = query)
            .map { exerciseDtoModel ->
                exerciseDtoModel.toExercisesSummaryDomainModel()
            }

        SearchDomainModel(
            query = query,
            exercisesSummaryDomainModel = ExercisesSummaryDomainModel(items = exercises)
        )
    }

    companion object {
        private const val DEBOUNCE = 500L
    }
}

sealed interface SearchActions {
    data class Search(val query: String) : SearchActions
    data object Retry : SearchActions
}