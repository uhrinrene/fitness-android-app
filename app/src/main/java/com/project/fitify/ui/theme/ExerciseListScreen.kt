package com.project.fitify.ui.theme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.ExerciseCodeUiModel
import com.project.fitify.ExerciseListViewModel
import com.project.fitify.ExercisePacksUiModel
import com.project.fitify.StatefulModel

@Composable
fun ExerciseListScreen(modifier: Modifier = Modifier, viewModel: ExerciseListViewModel, onExerciseClicked: (ExerciseCodeUiModel) -> Unit) {

    val state by viewModel.exerciseList.collectAsStateWithLifecycle()

    when(state) {
        is StatefulModel.Content<ExercisePacksUiModel> -> LazyColumn(modifier = modifier) {
            items(count = (state as StatefulModel.Content<ExercisePacksUiModel>).data.items.size) { index ->
                ExerciseItemView(model = (state as StatefulModel.Content<ExercisePacksUiModel>).data.items[index], onExerciseClicked = onExerciseClicked)
            }
        }
        is StatefulModel.Error -> Text("Error")
        is StatefulModel.Loading -> Text("Loading")
    }
}