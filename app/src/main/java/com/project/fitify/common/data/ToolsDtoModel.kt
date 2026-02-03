package com.project.fitify.common.data

data class ToolsDtoModel(
    val items: List<ToolDtoModel>
)

data class ToolDtoModel(
    val packCode: String,
    val exerciseCount: Int
)


fun ToolsResponseModel.toDtoModel() = ToolsDtoModel(items = tools.map {
    ToolDtoModel(
        packCode = it.packCode,
        exerciseCount = it.exerciseCount
    )
})