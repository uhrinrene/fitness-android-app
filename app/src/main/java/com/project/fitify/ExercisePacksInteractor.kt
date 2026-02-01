package com.project.fitify

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExerciseListInteractor(
    private val api: IExerciseRepository
) : ISimpleInteractor<ExercisePacksDomainModel> {

    // TODO Zkusit chybu
    // TODO vytvorit celej odkaz na obrazek
    // TODO nechci posilat celej objekt do ui mapperu
    // TODO hanndling coroutine myslim tim ten try catch
    // TODO Premysleni o rozdeleni tech dvou do interactoru a UseCasu, uvidim jeste
    // TODO think about naming
    override fun loadData(): Flow<ResultState<ExercisePacksDomainModel>> = flow {
        emit(value = ResultState.Loading)

        try {
            val exercises = coroutineScope {
                api.getExercisePacks().tools
                    .map { tool ->
                        async {
                            api.getExercises(pathCode = tool.packCode).exercises.map { exerciseDto ->
                                exerciseDto to tool.packCode
                            }
                        }
                    }
                    .awaitAll()
                    .flatten()
                    .map { (exerciseDto, packCode) ->
                        ExercisePackDomainModel(
                            thumbnailUrl = "",
                            title = exerciseDto.title,
                            packCode = packCode,
                            exerciseCode = exerciseDto.exerciseCode
                        )
                    }
            }

            emit(ResultState.Success(value = ExercisePacksDomainModel(items = exercises)))

        } catch (e: Exception) {
            emit(ResultState.Error(errorDomainModel = ErrorDomainModel(throwable = e, message = e.message ?: "Chyba při stahování dat")))
        }
    }
}

data class ExercisePacksDomainModel(val items: List<ExercisePackDomainModel>)

data class ExercisePackDomainModel(
    val thumbnailUrl: String,
    val title: String,
    val packCode: String,
    val exerciseCode: String
)