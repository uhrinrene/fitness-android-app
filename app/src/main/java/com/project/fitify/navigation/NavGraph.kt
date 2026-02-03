package com.project.fitify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.viewmodel.list.ListViewModel
import com.project.fitify.view.detail.DetailScreen
import com.project.fitify.view.list.ListScreen
import com.project.fitify.viewmodel.detail.DetailViewModel.Companion.EXERCISE_CODE
import com.project.fitify.viewmodel.detail.DetailViewModel.Companion.PACK_CODE
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.LIST.name
    ) {
        composable(route = Screens.LIST.name) {
            val viewModel: ListViewModel = koinViewModel()
            ListScreen(
                viewModel = viewModel,
                onExerciseClicked = { packCode, exerciseCode ->
                    navController.navigate(Screens.DETAIL.name + "/$packCode/$exerciseCode")
                }
            )
        }

        composable(
            route = Screens.DETAIL.name + "/{$PACK_CODE}/{$EXERCISE_CODE}",
            arguments = listOf(
                navArgument(PACK_CODE) { type = NavType.StringType },
                navArgument(EXERCISE_CODE) { type = NavType.StringType }
            )) {

            val viewModel: DetailViewModel = koinViewModel()
            DetailScreen(
                viewModel = viewModel,
                onBackClicked = {
                    navController.navigateUp()
                }
            )
        }
    }
}