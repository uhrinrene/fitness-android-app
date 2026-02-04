package com.project.fitify.common.data.dto

import com.project.fitify.common.data.response.ToolsResponseModelModel

data class ToolsDtoModel(
    val items: List<ToolDtoModel>
) : IDtoModel

data class ToolDtoModel(
    val packCode: String,
    val exerciseCount: Int
)


fun ToolsResponseModelModel.toDtoModel() = ToolsDtoModel(items = tools.map {
    ToolDtoModel(
        packCode = it.packCode,
        exerciseCount = it.exerciseCount
    )
})