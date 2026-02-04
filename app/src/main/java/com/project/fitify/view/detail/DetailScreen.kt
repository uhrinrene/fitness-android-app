package com.project.fitify.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.view.ExerciseVideoPlayer
import com.project.fitify.view.FontText
import com.project.fitify.view.StatefulView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBackClicked: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val player by viewModel.playerInstance.collectAsStateWithLifecycle()

    StatefulView(modifier = modifier, data = state.state, content = { contentUiModel ->

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        FontText(
                            fontSize = 18.sp,
                            text = contentUiModel.title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                modifier = Modifier.size(size = 24.dp),
                                painter = painterResource(id = contentUiModel.backRowRes),
                                contentDescription = "zpět",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .padding(paddingValues = innerPadding)
                    .padding(all = 16.dp)
            ) {
                item(key = "video") {
                    ExerciseVideoPlayer(player = player)
                }

                item(key = "text") {
                    FontText(
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp),
                        text = contentUiModel.detail,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    })
}