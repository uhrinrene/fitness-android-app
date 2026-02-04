package com.project.fitify.viewmodel.list.uimapping

import com.project.fitify.R
import com.project.fitify.contract.list.ListContract.Event
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.view.stateful.StatefulUiModel
import com.project.fitify.view.exercise.ExerciseUiModel
import com.project.fitify.view.search.SearchBarUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class ListUiMapper {

    fun provideContentState(
        domainModel: ExercisesSummaryDomainModel,
        query: String = "",
        uiEvent: (Event) -> Unit
    ) = StatefulUiModel.Content(
        data = ContentUiModel(
            image = R.drawable.logo,
            searchUiModel = SearchBarUiModel(
                hint = R.string.search,
                value = query,
                leadingIcon = R.drawable.search,
                onValueChange = { query ->
                    uiEvent(Event.Search(query = query))
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
        val image: Int,
        val searchUiModel: SearchBarUiModel,
        val items: ImmutableList<ExerciseUiModel>
    )
}