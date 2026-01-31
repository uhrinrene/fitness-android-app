package com.project.fitify

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExerciseListInteractor(private val api: ExerciseApi) : ISimpleInteractor {

    // TODO Zkusit chybu
    override fun loadData(): Flow<ResultState> = flow {
        emit(value = ResultState.Loading)
        emit(value = ResultState.Success(value = api.getExercises()))
    }
}