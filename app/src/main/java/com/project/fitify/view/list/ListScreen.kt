package com.project.fitify.view.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.contract.list.ListContract
import com.project.fitify.viewmodel.list.ListViewModel
import com.project.fitify.StatefulUiModel
import com.project.fitify.view.SearchBarView
import com.project.fitify.view.StatefulView
import com.project.fitify.view.exercise.ExerciseItemView
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel,
    onExerciseClicked: (String, String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ListContract.Effect.OpenDetailScreen -> {
                    onExerciseClicked(effect.packCode, effect.exerciseCode)
                }
            }
        }
    }

    val contentState = state.state as? StatefulUiModel.Content<ListUiMapper.ContentUiModel>
    val exerciseList = contentState?.data?.items ?: emptyList()

    StatefulView(modifier = modifier, data = state.state, content = { contentUiModel ->
        SearchBarView(model = contentUiModel.searchUiModel)
        LazyColumn(modifier = modifier) {
            items(
                count = exerciseList.size,
                key = { item -> exerciseList[item].id }
            ) { item ->
                ExerciseItemView(
                    model = exerciseList[item]
                )
            }
        }
    })
}