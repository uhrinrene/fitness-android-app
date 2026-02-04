package com.project.fitify.common.network

import com.project.fitify.common.data.dto.ExerciseDtoModel
import com.project.fitify.common.data.dto.ToolsDtoModel

interface IExerciseRepository {

    suspend fun getTools(): ToolsDtoModel

    suspend fun getExercises(packCode: String): List<ExerciseDtoModel>

    suspend fun searchExercises(query: String): List<ExerciseDtoModel>

    suspend fun getExercise(packCode: String, exerciseCode: String) : ExerciseDtoModel

    suspend fun getInstructions(): Map<String, String>
}