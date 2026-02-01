package com.project.fitify

// TODO premejslet, jestli neudelat dto objekt uz rovnou tady
// TODO mozna nebude packCode potreba primo v dto
class ExerciseRepository(private val api: ExerciseApi) : IExerciseRepository {

    private var cachedExercises = mutableMapOf<String, List<ExerciseDtoModel>>()

    override suspend fun getExercisePacks() = api.getExercisePacks()

    override suspend fun getExercises(packCode: String) = api.getExercises(pathCode = packCode).toExerciseDtoModel(packCode = packCode).apply {
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
}