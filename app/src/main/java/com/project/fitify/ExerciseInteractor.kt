package com.project.fitify

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

// TODO refactor cele tridy
// TODO retry
class ExerciseInteractor(
    private val repository: IExerciseRepository
) : IInteractor<ExerciseActions, ExerciseDomainModel> {

    private val _exerciseFlow = MutableSharedFlow<ExerciseActions>()

    override suspend fun sendAction(action: ExerciseActions) =
        _exerciseFlow.emit(value = action)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData(): Flow<ResultState<ExerciseDomainModel>> {
        return _exerciseFlow.flatMapLatest { action ->

            when (action) {
                is ExerciseActions.GetExercise -> flow {
                    try {
                        emit(value = ResultState.Loading)
                        coroutineScope {
                            val exerciseDtoModel = async {
                                repository.getExercise(
                                    packCode = action.packCode
                                        ?: throw IllegalArgumentException("PathCode is null!!"),
                                    exerciseCode = action.exerciseCode
                                        ?: throw IllegalArgumentException("ExerciseCode is null!!")
                                )
                            }

                            val instructions = async { repository.getInstructions() }
                            emit(
                                value = ResultState.Success(
                                    value = ExerciseDomainModel(
                                        videoUrl = getVideoUrl(
                                            packCode = exerciseDtoModel.await().packCode,
                                            exerciseCode = exerciseDtoModel.await().exerciseCode
                                        ),
                                        title = exerciseDtoModel.await().title,
                                        detail = getInstructions(
                                            instructions = instructions.await(),
                                            instructionDtoModel = exerciseDtoModel.await().instructions
                                        ),
                                    )
                                )
                            )
                        }
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

                ExerciseActions.Retry -> TODO()
            }
        }
    }

    fun getVideoUrl(packCode: String, exerciseCode: String) =
        "https://static.gofitify.com/exercises/$packCode/videos/$exerciseCode.mp4"

    private fun getInstructions(
        instructions: Map<String, String>,
        instructionDtoModel: InstructionsDtoModel?
    ): String {
        if (instructionDtoModel == null) return ""

        val allInstructionIds = instructionDtoModel.hints +
                instructionDtoModel.breathing +
                instructionDtoModel.harder +
                instructionDtoModel.easier

        return allInstructionIds
            .mapNotNull { id -> instructions[id] }
            .joinToString(separator = " ")
    }
}

sealed interface ExerciseActions {
    data class GetExercise(val packCode: String?, val exerciseCode: String?) : ExerciseActions
    data object Retry : ExerciseActions
}