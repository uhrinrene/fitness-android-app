package com.project.fitify.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToolsResponseModel(
    val tools: List<ToolModel>
) : IResponse

@Serializable
data class ToolModel(
    @SerialName("code")
    val packCode: String,
    @SerialName("exercise_count")
    val exerciseCount: Int,
    val size: Long,
    val version: Int
)
