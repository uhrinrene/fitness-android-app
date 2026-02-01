package com.project.fitify

import kotlinx.coroutines.flow.flow

class SearchInteractor(private val repository: IExerciseRepository) : IParamInteractor<String, List<ExerciseDtoModel>> {

    override fun loadData(arg: String) = flow {
        emit(value = ResultState.Loading)
        emit(value = ResultState.Success(value = repository.searchExercises(query = arg)))
    }
}