package com.project.fitify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponse(
    val exercises: List<ExerciseModel>
) : IResponse

@Serializable
data class ExerciseModel(
    @SerialName("code")
    val exerciseCode: String,
    val title: String,
    val category: Map<String, Int>,
    val stance: String,
    @SerialName("skill_required") val skillRequired: Int,
    @SerialName("skill_max") val skillMax: Int,
    val sexyness: Int,
    @SerialName("looks_cool") val looksCool: Int,
    val impact: Int,
    val noisy: Int,
    @SerialName("change_sides") val changeSides: Boolean,
    val sets: Map<String, SetInfo>,
    @SerialName("constraint_positive") val constraintPositive: String,
    @SerialName("constraint_negative") val constraintNegative: String,
    val duration: Int,
    val reps: Int?,
    @SerialName("reps_double") val repsDouble: Boolean,
    @SerialName("reps_count_times") val repsCountTimes: List<Double> = emptyList(),
    @SerialName("reps_hint") val repsHint: String? = null,
    @SerialName("reps_preferred") val repsPreferred: Boolean,
    @SerialName("weight_supported") val weightSupported: Boolean,
    val tool: String?,
    @SerialName("muscle_intensity") val muscleIntensity: Map<String, Int> = emptyMap(),
    @SerialName("muscle_intensity_stretch") val muscleIntensityStretch: Map<String, Int> = emptyMap(),
    val instructions: InstructionsModel? = null
)

@Serializable
data class SetInfo(
    val suitability: Int? = null,
    val difficulty: Int? = null,
    val order: Int? = null,
    @SerialName("skill_required") val skillRequired: Int? = null,
    @SerialName("skill_max") val skillMax: Int? = null,
    // Některé sety mají extra parametry pro konkrétní partie
    @SerialName("suitability_lowerbody") val suitabilityLowerBody: Int? = null,
    @SerialName("suitability_abscore") val suitabilityAbsCore: Int? = null,
    @SerialName("suitability_back") val suitabilityBack: Int? = null,
    @SerialName("suitability_upperbody") val suitabilityUpperBody: Int? = null
)

@Serializable
data class InstructionsModel(
    val hints: List<String> = emptyList(),
    val breathing: List<String> = emptyList(),
    val harder: List<String> = emptyList(),
    val easier: List<String> = emptyList()
)