package com.project.fitify

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<ISimpleInteractor<ExercisePacksDomainModel>> {
        ExerciseListInteractor(api = get())
    }

    factory<IParamInteractor<String, List<ExerciseDtoModel>>> {
        SearchInteractor(repository = get())
    }

    single<IExerciseRepository> {
        ExerciseRepository(api = get<ExerciseApi>())
    }

    factory { ExerciseListUiMapper() }

    viewModel {
        ExerciseListViewModel(
            exerciseListInteractor = get(),
            exerciseListUiMapper = get(),
            searchInteractor = get()
        )
    }
}