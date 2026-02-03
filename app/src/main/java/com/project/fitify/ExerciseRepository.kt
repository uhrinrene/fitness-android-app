package com.project.fitify

import com.project.fitify.common.IInstructionLocalSource
import com.project.fitify.common.data.ExerciseDtoModel
import com.project.fitify.common.data.toDtoModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

// TODO premejslet, jestli neudelat dto objekt uz rovnou tady
// TODO mozna nebude packCode potreba primo v dto
// TODO nevadi, ze je to mutable?
// TODO upravit metodu getInstructions nejsem si jistej, ze je pekne napsana
// TODO setridit to abecedne
class ExerciseRepository(
    private val api: ExerciseApi,
    private val localSource: IInstructionLocalSource,
    private val json: Json
) : IExerciseRepository {

    private var cachedExercises = mutableMapOf<String, List<ExerciseDtoModel>>()

    private var instructionsCache = mutableMapOf<String, String>()

    private val mapMutex = Mutex()

    override suspend fun getTools() = api.getTools().toDtoModel()

    override suspend fun getExercises(packCode: String) =
        api.getExercises(pathCode = packCode).toDtoModel(packCode = packCode).apply {
            cachedExercises[packCode] = this
        }

    override suspend fun searchExercises(query: String): List<ExerciseDtoModel> {
        val allExercises = cachedExercises.values.flatten()

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
        val exercisesInPack = mapMutex.withLock {
            cachedExercises[packCode]
        } ?: getExercises(packCode)

        return exercisesInPack.find { it.exerciseCode == exerciseCode }
            ?: throw NoSuchElementException("Exercise $exerciseCode is not found in pack $packCode")
    }

    override suspend fun getInstructions() = mapMutex.withLock {
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