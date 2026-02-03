package com.project.fitify.model.list

import com.project.fitify.IExerciseRepository
import com.project.fitify.common.IInteractor
import com.project.fitify.ResultState
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.loadResultState
import com.project.fitify.model.list.domainmapping.toExercisesSummaryDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest

class ListInteractor(
    private val repository: IExerciseRepository
) : IInteractor<ListActions, ExercisesSummaryDomainModel> {

    private val _exerciseListFlow = MutableSharedFlow<ListActions>()

    override suspend fun sendAction(action: ListActions) =
        _exerciseListFlow.emit(value = action)

    // TODO Zkusit chybu
    // TODO formatovani
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData(): Flow<ResultState<ExercisesSummaryDomainModel>> {

        return _exerciseListFlow.flatMapLatest { action ->
            when (action) {
                ListActions.LoadData -> getTools()
            }
        }
    }

    private fun getTools() = loadResultState { scope ->
        val exercises = repository.getTools().items
            .map { tool ->
                scope.async {
                    repository.getExercises(packCode = tool.packCode)
                        .map { exerciseDto ->
                            exerciseDto
                        }
                }
            }
            .awaitAll()
            .flatten()
            .map { exerciseDtoModel ->
                exerciseDtoModel.toExercisesSummaryDomainModel()
            }

        ExercisesSummaryDomainModel(
            items = exercises
        )
    }
}

sealed interface ListActions {
    data object LoadData : ListActions
}