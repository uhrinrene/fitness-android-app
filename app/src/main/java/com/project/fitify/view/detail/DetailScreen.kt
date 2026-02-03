package com.project.fitify.view.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.view.ExerciseVideoPlayer
import com.project.fitify.StatefulUiModel
import com.project.fitify.viewmodel.detail.uimapping.DetailUiMapper

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBackClicked: () -> Unit) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val player by viewModel.playerInstance.collectAsStateWithLifecycle()

    when (state.state) {
        is StatefulUiModel.Content<DetailUiMapper.ContentUiModel> -> Column {
            LazyColumn(modifier = modifier) {
                item(key = "video") {
                    ExerciseVideoPlayer(player = player)
                    Text((state.state as StatefulUiModel.Content<DetailUiMapper.ContentUiModel>).data.detail)
                }
            }
        }

        is StatefulUiModel.Error -> Text("Error")
        is StatefulUiModel.Loading -> Text("Loading")
    }
}