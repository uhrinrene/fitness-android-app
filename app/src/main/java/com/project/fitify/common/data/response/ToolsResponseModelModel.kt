package com.project.fitify.common.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToolsResponseModelModel(
    val tools: List<ToolModel>
) : IResponseModel

@Serializable
data class ToolModel(
    @SerialName("code")
    val packCode: String,
    @SerialName("exercise_count")
    val exerciseCount: Int,
    val size: Long,
    val version: Int
)
