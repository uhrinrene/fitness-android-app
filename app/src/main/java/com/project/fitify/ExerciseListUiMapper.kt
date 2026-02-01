package com.project.fitify

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// TODO udelat interface nad mapperem
// TODO premyslet nad nazvama, myslim si, ze nejsou uplne top notch
class ExerciseListUiMapper {

    fun provideContentState(domainModel: ExercisePacksDomainModel) =
        StatefulModel.Content(data = ExercisePacksUiModel(items = domainModel.items.map { item ->
            ExercisePackUiModel(
                icon = item.thumbnailUrl,
                title = item.title,
                onClick = {
                    ExerciseCodeUiModel(packCode = item.packCode, exerciseCode = item.exerciseCode)
                })
        }.toImmutableList()))
}

data class ExercisePacksUiModel(val items: ImmutableList<ExercisePackUiModel>)

data class ExercisePackUiModel(
    val icon: String,
    val title: String,
    val onClick: () -> ExerciseCodeUiModel
)

data class ExerciseCodeUiModel(val packCode: String, val exerciseCode: String)