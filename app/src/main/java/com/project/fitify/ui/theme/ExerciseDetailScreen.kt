package com.project.fitify.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.ExerciseDetailViewModel
import com.project.fitify.ExerciseUiModel
import com.project.fitify.ExerciseVideoPlayer
import com.project.fitify.StatefulModel

@Composable
fun ExerciseDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ExerciseDetailViewModel,
    onBackClicked: () -> Unit) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val player by viewModel.playerInstance.collectAsStateWithLifecycle()

    when (state.state) {
        is StatefulModel.Content<ExerciseUiModel> -> Column {
            LazyColumn(modifier = modifier) {
                item(key = "video") {
                    ExerciseVideoPlayer(player = player)
                    Text((state.state as StatefulModel.Content<ExerciseUiModel>).data.detail)
                }
            }
        }

        is StatefulModel.Error -> Text("Error")
        is StatefulModel.Loading -> Text("Loading")
    }

}