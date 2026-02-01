package com.project.fitify

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
    val sets: Map<String, SetInfo>,
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
    val instructions: InstructionsDto? = null
)

data class InstructionsDto(
    val hints: List<String> = emptyList(),
    val breathing: List<String> = emptyList(),
    val harder: List<String> = emptyList(),
    val easier: List<String> = emptyList()
)