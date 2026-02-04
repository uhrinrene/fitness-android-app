package com.project.fitify.view.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.fitify.contract.list.ListContract
import com.project.fitify.viewmodel.list.ListViewModel
import com.project.fitify.view.stateful.StatefulUiModel
import com.project.fitify.view.search.SearchBarView
import com.project.fitify.view.stateful.StatefulView
import com.project.fitify.view.exercise.ExerciseItemView
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper

@OptIn(ExperimentalMaterial3Api::class)
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

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Image(
                            modifier = Modifier.size(116.dp),
                            contentDescription = "Logo",
                            painter = painterResource(id = contentUiModel.image)
                        )
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

            Column(modifier = modifier.padding(paddingValues = innerPadding)) {
                SearchBarView(model = contentUiModel.searchUiModel)
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
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

        }
    })
}