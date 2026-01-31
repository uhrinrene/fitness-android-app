package com.project.fitify.ui.theme

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.project.fitify.ExerciseListViewModel

@Composable
fun ExerciseListScreen(modifier: Modifier = Modifier, viewModel: ExerciseListViewModel) {

    val state by viewModel.exerciseList.collectAsState()

    LazyColumn(modifier = modifier) {
        items(state.size) { index ->
            ExerciseItemView(model = state[index])
        }
    }
}