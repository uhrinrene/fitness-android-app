package com.project.fitify

interface IExerciseRepository {

    suspend fun getExercisePacks(): ToolsResponse

    suspend fun getExercises(pathCode: String): ExerciseResponse

}