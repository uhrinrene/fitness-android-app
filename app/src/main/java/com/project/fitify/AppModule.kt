package com.project.fitify

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory<IInteractor<ExercisePacksActions, ExercisePacksDomainModel>>(qualifier = named(name = "exerciseList")) {
        ExerciseListInteractor(repository = get())
    }

    factory<IInteractor<SearchActions, ExercisePacksDomainModel>>(qualifier = named(name = "search")) {
        SearchInteractor(repository = get())
    }

    single<IExerciseRepository> {
        ExerciseRepository(api = get<ExerciseApi>())
    }

    factory { ExerciseListUiMapper() }

    viewModel {
        ExerciseListViewModel(
            exerciseListInteractor = get(qualifier = named(name = "exerciseList")),
            exerciseListUiMapper = get(),
            searchInteractor = get(qualifier = named(name = "search"))
        )
    }
}