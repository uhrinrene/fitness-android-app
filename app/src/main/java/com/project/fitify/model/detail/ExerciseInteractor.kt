package com.project.fitify.model.detail

import com.project.fitify.IExerciseRepository
import com.project.fitify.ResultState
import com.project.fitify.common.IInteractor
import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.common.data.InstructionsDtoModel
import com.project.fitify.loadResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest

class ExerciseInteractor(
    private val repository: IExerciseRepository
) : IInteractor<ExerciseActions, ExerciseDomainModel> {

    private val _exerciseFlow = MutableSharedFlow<ExerciseActions>()

    override suspend fun sendAction(action: ExerciseActions) = _exerciseFlow.emit(value = action)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData(): Flow<ResultState<ExerciseDomainModel>> {

        var packCode: String? = null
        var exerciseCode: String? = null

        return _exerciseFlow.flatMapLatest { action ->

            when (action) {
                is ExerciseActions.LoadExercise -> {
                    packCode = action.packCode
                    exerciseCode = action.exerciseCode
                    getExercise(
                        packCode = packCode,
                        exerciseCode = exerciseCode
                    )
                }

                ExerciseActions.Retry -> getExercise(
                    packCode = packCode,
                    exerciseCode = exerciseCode
                )
            }
        }
    }

    private fun getExercise(packCode: String?, exerciseCode: String?) = loadResultState { scope ->
        val exerciseDtoModel = scope.async {
            repository.getExercise(
                packCode = packCode ?: throw IllegalArgumentException("PathCode is null!!"),
                exerciseCode = exerciseCode
                    ?: throw IllegalArgumentException("ExerciseCode is null!!")
            )
        }

        ExerciseDomainModel(
            videoUrl = getVideoUrl(
                packCode = exerciseDtoModel.await().packCode,
                exerciseCode = exerciseDtoModel.await().exerciseCode
            ),
            title = exerciseDtoModel.await().title,
            detail = getInstructions(
                instructions = scope.async { repository.getInstructions() }.await(),
                instructionDtoModel = exerciseDtoModel.await().instructions
            ),
        )
    }

    private fun getVideoUrl(packCode: String, exerciseCode: String) =
        "https://static.gofitify.com/exercises/$packCode/videos/$exerciseCode.mp4"

    private fun getInstructions(
        instructions: Map<String, String>, instructionDtoModel: InstructionsDtoModel?
    ): String {
        if (instructionDtoModel == null) return ""

        val allInstructionIds =
            instructionDtoModel.hints + instructionDtoModel.breathing + instructionDtoModel.harder + instructionDtoModel.easier

        return allInstructionIds.mapNotNull { id -> instructions[id] }.joinToString(separator = " ")
    }
}

sealed interface ExerciseActions {
    data class LoadExercise(val packCode: String?, val exerciseCode: String?) : ExerciseActions
    data object Retry : ExerciseActions
}