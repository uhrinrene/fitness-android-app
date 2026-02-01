package com.project.fitify

fun ExerciseResponse.toExerciseDtoModel(packCode: String) = this.exercises.map { exerciseModel ->
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
            InstructionsDto(
                hints = it.hints,
                breathing = it.breathing,
                harder = it.harder,
                easier = it.easier
            )
        }
    )
}