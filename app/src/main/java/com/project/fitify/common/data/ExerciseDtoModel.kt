package com.project.fitify.common.data

data class ExerciseDtoModel(
    val exerciseCode: String,
    val packCode: String,
    val title: String,
    val category: Map<String, Int>,
    val stance: String,
    val skillRequired: Int,
    val skillMax: Int,
    val sexyness: Int,
    val looksCool: Int,
    val impact: Int,
    val noisy: Int,
    val changeSides: Boolean,
    val sets: Map<String, SetInfoModel>,
    val constraintPositive: String,
    val constraintNegative: String,
    val duration: Int,
    val reps: Int?,
    val repsDouble: Boolean,
    val repsCountTimes: List<Double> = emptyList(),
    val repsHint: String? = null,
    val repsPreferred: Boolean,
    val weightSupported: Boolean,
    val tool: String?,
    val muscleIntensity: Map<String, Int> = emptyMap(),
    val muscleIntensityStretch: Map<String, Int> = emptyMap(),
    val instructions: InstructionsDtoModel? = null
)

data class InstructionsDtoModel(
    val hints: List<String> = emptyList(),
    val breathing: List<String> = emptyList(),
    val harder: List<String> = emptyList(),
    val easier: List<String> = emptyList()
)

fun ExercisesResponseModel.toDtoModel(packCode: String) = this.exercises.map { exerciseModel ->
    ExerciseDtoModel(
        exerciseCode = exerciseModel.exerciseCode,
        packCode = packCode,
        title = exerciseModel.title,
        category = exerciseModel.category,
        stance = exerciseModel.stance,
        skillRequired = exerciseModel.skillRequired,
        skillMax = exerciseModel.skillMax,
        sexyness = exerciseModel.sexyness,
        looksCool = exerciseModel.looksCool,
        impact = exerciseModel.impact,
        noisy = exerciseModel.noisy,
        changeSides = exerciseModel.changeSides,
        sets = exerciseModel.sets,
        constraintPositive = exerciseModel.constraintPositive,
        constraintNegative = exerciseModel.constraintNegative,
        duration = exerciseModel.duration,
        reps = exerciseModel.reps,
        repsDouble = exerciseModel.repsDouble,
        repsCountTimes = exerciseModel.repsCountTimes,
        repsHint = exerciseModel.repsHint,
        repsPreferred = exerciseModel.repsPreferred,
        weightSupported = exerciseModel.weightSupported,
        tool = exerciseModel.tool,
        muscleIntensity = exerciseModel.muscleIntensity,
        muscleIntensityStretch = exerciseModel.muscleIntensityStretch,
        instructions = exerciseModel.instructions?.let {
            InstructionsDtoModel(
                hints = it.hints,
                breathing = it.breathing,
                harder = it.harder,
                easier = it.easier
            )
        }
    )
}