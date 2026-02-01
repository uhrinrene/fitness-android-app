package com.project.fitify

interface IExerciseRepository {

    suspend fun getExercisePacks(): ToolsResponse

    suspend fun getExercises(packCode: String): List<ExerciseDtoModel>

    suspend fun searchExercises(query: String): List<ExerciseDtoModel>
}