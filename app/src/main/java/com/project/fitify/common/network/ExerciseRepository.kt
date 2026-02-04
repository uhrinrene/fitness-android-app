package com.project.fitify.common.network

import com.project.fitify.common.IInstructionLocalSource
import com.project.fitify.common.data.dto.ExerciseDtoModel
import com.project.fitify.common.data.dto.toDtoModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

class ExerciseRepository(
    private val api: ExerciseApi,
    private val localSource: IInstructionLocalSource,
    private val json: Json
) : IExerciseRepository {

    private var cachedExercises = mutableMapOf<String, List<ExerciseDtoModel>>()

    private var instructionsCache = mutableMapOf<String, String>()

    private val mutex = Mutex()

    override suspend fun getTools() = api.getTools().toDtoModel()

    override suspend fun getExercises(packCode: String) =
        api.getExercises(pathCode = packCode).toDtoModel(packCode = packCode).apply {
            mutex.withLock {
                cachedExercises[packCode] = this
            }
        }

    override suspend fun searchExercises(query: String): List<ExerciseDtoModel> {
        val allExercises = mutex.withLock {
            cachedExercises.values.flatten()
        }

        if (query.isBlank()) {
            return allExercises
        }

        return allExercises.filter { exercise ->
            exercise.title.contains(query, ignoreCase = true)
        }
    }

    override suspend fun getExercise(
        packCode: String,
        exerciseCode: String
    ): ExerciseDtoModel {
        val exercisesInPack = mutex.withLock {
            cachedExercises[packCode]
        } ?: getExercises(packCode)

        return exercisesInPack.find { it.exerciseCode == exerciseCode }
            ?: throw NoSuchElementException("Exercise $exerciseCode is not found in pack $packCode")
    }

    override suspend fun getInstructions() = mutex.withLock {
        if (instructionsCache.isNotEmpty()) {
            return@withLock instructionsCache
        }

        try {
            val jsonString = localSource.getInstructionsJson(fileName = INSTRUCTIONS_FILE_NAME)
            val parsedMap = json.decodeFromString<Map<String, String>>(jsonString)

            instructionsCache.putAll(parsedMap)
            instructionsCache
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        private const val INSTRUCTIONS_FILE_NAME = "instructions"
    }
}