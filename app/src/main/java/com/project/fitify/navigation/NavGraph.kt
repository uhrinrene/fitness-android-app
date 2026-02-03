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
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.viewmodel.list.ListViewModel
import com.project.fitify.view.detail.ExerciseDetailScreen
import com.project.fitify.view.list.ExerciseListScreen
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
                val viewModel: ListViewModel = koinViewModel()
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

                val viewModel: DetailViewModel = koinViewModel()
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