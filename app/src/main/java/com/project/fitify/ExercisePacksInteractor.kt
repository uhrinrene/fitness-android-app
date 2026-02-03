package com.project.fitify

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class ExerciseListInteractor(
    private val repository: IExerciseRepository
) : IInteractor<ExercisePacksActions, ExercisePacksDomainModel> {

    private val _exerciseListFlow = MutableSharedFlow<ExercisePacksActions>()

    override suspend fun sendAction(action: ExercisePacksActions) = _exerciseListFlow.emit(value = action)

    // TODO Zkusit chybu
    // TODO vytvorit celej odkaz na obrazek
    // TODO nechci posilat celej objekt do ui mapperu
    // TODO hanndling coroutine myslim tim ten try catch
    // TODO Premysleni o rozdeleni tech dvou do interactoru a UseCasu, uvidim jeste
    // TODO think about naming
    // TODO formatovani
    // TODO rozsekat to na casti
    // TODO retry
    // TODO packCode uz je soucasti
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData(): Flow<ResultState<ExercisePacksDomainModel>> {
        return _exerciseListFlow.flatMapLatest {

            flow {
                try {
                    emit(value = ResultState.Loading)
                    val exercises = coroutineScope {
                        repository.getExercisePacks().tools
                            .map { tool ->
                                async {
                                    repository.getExercises(packCode = tool.packCode)
                                        .map { exerciseDto ->
                                            exerciseDto to tool.packCode
                                        }
                                }
                            }
                            .awaitAll()
                            .flatten()
                            .map { (exerciseDtoModel, packCode) ->
                                ExercisePacksDomainModel.ExercisePackDomainModel(
                                    thumbnailUrl = getThumbnailUrl(packCode = packCode, exerciseCode = exerciseDtoModel.exerciseCode),
                                    title = exerciseDtoModel.title,
                                    packCode = packCode,
                                    exerciseCode = exerciseDtoModel.exerciseCode
                                )
                            }
                    }

                    emit(ResultState.Success(value = ExercisePacksDomainModel(items = exercises)))

                } catch (e: Exception) {
                    emit(
                        ResultState.Error(
                            errorDomainModel = ErrorDomainModel(
                                throwable = e,
                                message = e.message ?: "Chyba při stahování dat"
                            )
                        )
                    )
                }
            }
        }
    }
}

sealed interface ExercisePacksActions {
    data object LoadData : ExercisePacksActions
    data object Retry : ExercisePacksActions
}