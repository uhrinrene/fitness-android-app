package com.project.fitify

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SearchInteractor(private val repository: IExerciseRepository) :
    IInteractor<SearchActions, ExercisePacksDomainModel> {

    private val _searchFlow = MutableSharedFlow<SearchActions>()

    override suspend fun sendAction(action: SearchActions) = _searchFlow.emit(value = action)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData(): Flow<ResultState<ExercisePacksDomainModel>> {
        return _searchFlow.flatMapLatest { action ->
            when (action) {
                SearchActions.Retry -> TODO()
                is SearchActions.Search -> search(query = action.query)
            }
        }
    }

    private fun search(query: String): Flow<ResultState<ExercisePacksDomainModel>> = flow {
        try {
            emit(value = ResultState.Loading)
            val exercises = repository.searchExercises(query = query)
                .map { exerciseDto ->
                    ExercisePacksDomainModel.ExercisePackDomainModel(
                        thumbnailUrl = "",
                        title = exerciseDto.title,
                        packCode = exerciseDto.packCode,
                        exerciseCode = exerciseDto.exerciseCode
                    )
                }
            emit(value = ResultState.Success(value = ExercisePacksDomainModel(items = exercises)))
        } catch (e: Exception) {
            emit(
                ResultState.Error(
                    errorDomainModel = ErrorDomainModel(
                        throwable = e,
                        message = e.message ?: "Chyba při stahování dat"
                    )
                )
            )
        }
    }

}

sealed interface SearchActions {
    data class Search(val query: String) : SearchActions
    data object Retry : SearchActions
}