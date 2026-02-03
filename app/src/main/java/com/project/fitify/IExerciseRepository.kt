package com.project.fitify

import com.project.fitify.common.data.ExerciseDtoModel
import com.project.fitify.common.data.ToolsDtoModel

interface IExerciseRepository {

    suspend fun getTools(): ToolsDtoModel

    suspend fun getExercises(packCode: String): List<ExerciseDtoModel>

    suspend fun searchExercises(query: String): List<ExerciseDtoModel>

    suspend fun getExercise(packCode: String, exerciseCode: String) : ExerciseDtoModel

    suspend fun getInstructions(): Map<String, String>
}