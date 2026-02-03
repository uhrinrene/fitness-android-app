package com.project.fitify.view.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.view.ExerciseVideoPlayer
import com.project.fitify.view.StatefulView

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBackClicked: () -> Unit) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val player by viewModel.playerInstance.collectAsStateWithLifecycle()

    StatefulView(modifier = modifier, data = state.state, content = { contentUiModel ->
        LazyColumn(modifier = modifier) {
            item(key = "video") {
                ExerciseVideoPlayer(player = player)
                Text(text = contentUiModel.detail)
            }
        }
    })
}