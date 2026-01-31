package com.project.fitify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToolsResponse(
    val tools: List<Tool>
) : IResponse

@Serializable
data class Tool(
    val code: String,
    @SerialName("exercise_count")
    val exerciseCount: Int,
    val size: Long,
    val version: Int
)
