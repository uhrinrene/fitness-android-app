package com.project.fitify.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.ExerciseListContract
import com.project.fitify.ExerciseListViewModel
import com.project.fitify.ExercisePacksUiModel
import com.project.fitify.StatefulModel
import com.project.fitify.navigation.ExerciseSearchBar

@Composable
fun ExerciseListScreen(
    modifier: Modifier = Modifier,
    viewModel: ExerciseListViewModel,
    onExerciseClicked: (String, String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ExerciseListContract.Effect.OpenDetailScreen -> {
                    onExerciseClicked(effect.packCode, effect.exerciseCode)
                }
            }
        }
    }

    val contentState = state.state as? StatefulModel.Content<ExercisePacksUiModel>
    val exerciseList = contentState?.data?.items ?: emptyList()

    // TODO podivat se proc je potreba ten cast
    when (state.state) {
        is StatefulModel.Content<ExercisePacksUiModel> -> Column {
            ExerciseSearchBar(model = (state.state as StatefulModel.Content<ExercisePacksUiModel>).data.searchUiModel)
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
        }

        is StatefulModel.Error -> Text("Error")
        is StatefulModel.Loading -> Text("Loading")
    }
}