package com.project.fitify

class ExerciseRepository(private val api: ExerciseApi) : IExerciseRepository {

    override suspend fun getExercisePacks() = api.getExercisePacks()

    override suspend fun getExercises(pathCode: String) = api.getExercises(pathCode = pathCode)
}