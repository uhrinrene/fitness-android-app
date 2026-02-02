package com.project.fitify

import org.koin.android.ext.koin.androidContext
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

    factory { ExerciseDetailUiMapper() }

    factory<IVideoPlayerHandler> {
        AndroidVideoPlayerHandler(context = androidContext())
    }

    factory<IInteractor<ExerciseActions, ExerciseDomainModel>>(qualifier = named(name = "exercise")) {
        ExerciseInteractor(repository = get())
    }

    single<IExerciseRepository> {
        ExerciseRepository(api = get<ExerciseApi>(), localSource = get(), json = get())
    }

    factory { ExerciseListUiMapper() }

    factory { ExerciseDetailUiMapper() }

    viewModel {
        ExerciseListViewModel(
            exerciseListInteractor = get(qualifier = named(name = "exerciseList")),
            exerciseListUiMapper = get(),
            searchInteractor = get(qualifier = named(name = "search"))
        )
    }

    viewModel {
        ExerciseDetailViewModel(
            savedStateHandle = get(),
            exerciseInteractor = get(qualifier = named(name = "exercise")),
            exerciseDetailUiMapper = get(),
            playerHandler = get()
        )
    }
}