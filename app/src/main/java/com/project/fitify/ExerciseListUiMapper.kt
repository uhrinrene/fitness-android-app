package com.project.fitify

import com.project.fitify.ExerciseListContract.Event
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// TODO udelat interface nad mapperem
// TODO premyslet nad nazvama, myslim si, ze nejsou uplne top notch
class ExerciseListUiMapper {

    fun provideContentState(
        domainModel: ExercisePacksDomainModel,
        uiEvent: (Event) -> Unit
    ) = StatefulModel.Content(
        data = ExercisePacksUiModel(
            searchUiModel = SearchUiModel(
                hint = "Search",
                value = "",
                onValueChange = { query ->
                    uiEvent(Event.OnValueChanged(query = query))
                }), items = domainModel.items.mapIndexed { index, item ->
                ExercisePackUiModel(
                    id = index,
                    icon = item.thumbnailUrl,
                    title = item.title,
                    onClick = {
                        uiEvent(
                            Event.OnExerciseClicked(
                                packCode = item.packCode,
                                exerciseCode = item.exerciseCode
                            )
                        )
                    })
            }.toImmutableList()
        )
    )
}

data class ExercisePacksUiModel(
    val searchUiModel: SearchUiModel,
    val items: ImmutableList<ExercisePackUiModel>
)

data class SearchUiModel(val value: String, val hint: String, val onValueChange: (String) -> Unit)

data class ExercisePackUiModel(
    val id: Int,
    val icon: String,
    val title: String,
    val onClick: () -> Unit
)