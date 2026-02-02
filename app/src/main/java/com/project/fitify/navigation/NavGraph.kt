package com.project.fitify.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.project.fitify.ExerciseDetailViewModel
import com.project.fitify.ExerciseListViewModel
import com.project.fitify.ui.theme.ExerciseDetailScreen
import com.project.fitify.ui.theme.ExerciseListScreen
import org.koin.androidx.compose.koinViewModel

// TODO popremyslet o tech routach
@Composable
fun NavGraph(modifier: PaddingValues, navController: NavHostController) {
    Box(modifier = Modifier.padding(paddingValues = modifier)) {
        NavHost(
            navController = navController,
            startDestination = "exercise_list"
        ) {
            composable(route = "exercise_list") {
                val viewModel: ExerciseListViewModel = koinViewModel()
                ExerciseListScreen(
                    viewModel = viewModel,
                    onExerciseClicked = { packCode, exerciseCode ->
                        navController.navigate("exercise_detail/$packCode/$exerciseCode")
                    }
                )
            }

            composable(route = "exercise_detail/{packCode}/{exerciseCode}", arguments = listOf(
                navArgument("packCode") { type = NavType.StringType },
                navArgument("exerciseCode") { type = NavType.StringType }
            )) {

                val viewModel: ExerciseDetailViewModel = koinViewModel()
                ExerciseDetailScreen(
                    viewModel = viewModel,
                    onBackClicked = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}