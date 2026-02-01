package com.project.fitify.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.project.fitify.ExerciseDetailScreen
import com.project.fitify.ExerciseDetailViewModel
import com.project.fitify.ExerciseListViewModel
import com.project.fitify.ui.theme.ExerciseListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(modifier: PaddingValues, navController: NavHostController) {
    Box(modifier = Modifier.padding(paddingValues = modifier)) {
        NavHost(
            navController = navController,
            startDestination = "Screen.ExerciseList"
        ) {
            composable(route = "Screen.ExerciseList") {
                val viewModel: ExerciseListViewModel = koinViewModel()
                ExerciseListScreen(
                    viewModel = viewModel,
                    onExerciseClicked = { callback ->
                        navController.navigate("Screen.ExerciseDetail")
                    }
                )
            }

            composable(route = "Screen.ExerciseDetail") {
//                val viewModel: ExerciseDetailViewModel = koinViewModel()
                Text("Detail")
//                ExerciseDetailScreen(
//                    viewModel = viewModel,
//                    exerciseId = exerciseDetail.exerciseId,
//                    onBackClick = {
//                        navController.navigateUp()
//                    }
//                )
            }
        }
    }
}