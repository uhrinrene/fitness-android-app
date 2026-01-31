package com.project.fitify

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { ExerciseListInteractor(get()) }

    viewModel { ExerciseListViewModel(exerciseListInteractor = get()) }
}