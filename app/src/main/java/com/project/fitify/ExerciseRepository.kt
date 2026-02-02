package com.project.fitify

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json

// TODO premejslet, jestli neudelat dto objekt uz rovnou tady
// TODO mozna nebude packCode potreba primo v dto
// TODO zabezpecit mapu cache pred concurency
// TODO nevadi, ze je to mutable?
// TODO upravit metodu getInstructions nejsem si jistej, ze je pekne napsana
class ExerciseRepository(
    private val api: ExerciseApi,
    private val localSource: IInstructionLocalSource,
    private val json: Json
) : IExerciseRepository {

    private var cachedExercises = mutableMapOf<String, List<ExerciseDtoModel>>()

    private var instructionsCache = mutableMapOf<String, String>()

    private val mapMutex = Mutex()

    override suspend fun getExercisePacks() = api.getExercisePacks()

    override suspend fun getExercises(packCode: String) =
        api.getExercises(pathCode = packCode).toExerciseDtoModel(packCode = packCode).apply {
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
            val jsonString = localSource.getInstructionsJson()
            val parsedMap = json.decodeFromString<Map<String, String>>(jsonString)

            instructionsCache.putAll(parsedMap)
            instructionsCache
        } catch (e: Exception) {
            throw e
        }
    }
}