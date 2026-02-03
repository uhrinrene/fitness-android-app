package com.project.fitify.viewmodel.list.uimapping

import com.project.fitify.contract.list.ListContract.Event
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.StatefulModel
import com.project.fitify.view.exercise.ExerciseUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// TODO udelat interface nad mapperem
// TODO premyslet nad nazvama, myslim si, ze nejsou uplne top notch
class ListUiMapper {

    fun provideContentState(
        domainModel: ExercisesSummaryDomainModel,
        uiEvent: (Event) -> Unit
    ) = StatefulModel.Content(
        data = ContentUiModel(
            searchUiModel = SearchUiModel(
                hint = "Search",
                value = "",
                onValueChange = { query ->
                    uiEvent(Event.OnValueChanged(query = query))
                }), items = domainModel.items.mapIndexed { index, item ->
                ExerciseUiModel(
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

    data class ContentUiModel(
        val searchUiModel: SearchUiModel,
        val items: ImmutableList<ExerciseUiModel>
    )
}

data class SearchUiModel(val value: String, val hint: String, val onValueChange: (String) -> Unit)