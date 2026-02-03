package com.project.fitify

import com.project.fitify.common.IInteractor
import com.project.fitify.common.IVideoPlayerHandler
import com.project.fitify.model.detail.domainmapping.ExerciseDomainModel
import com.project.fitify.model.list.domainmapping.ExercisesSummaryDomainModel
import com.project.fitify.model.detail.ExerciseActions
import com.project.fitify.model.detail.ExerciseInteractor
import com.project.fitify.model.list.ListInteractor
import com.project.fitify.model.list.ListActions
import com.project.fitify.model.search.SearchActions
import com.project.fitify.model.search.SearchInteractor
import com.project.fitify.model.search.domainmapping.SearchDomainModel
import com.project.fitify.viewmodel.detail.DetailViewModel
import com.project.fitify.viewmodel.detail.uimapping.DetailUiMapper
import com.project.fitify.viewmodel.list.ListViewModel
import com.project.fitify.viewmodel.list.uimapping.ListUiMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory<IInteractor<ListActions, ExercisesSummaryDomainModel>>(qualifier = named(name = "exerciseList")) {
        ListInteractor(repository = get())
    }

    factory<IInteractor<SearchActions, SearchDomainModel>>(qualifier = named(name = "search")) {
        SearchInteractor(repository = get())
    }

    factory { DetailUiMapper() }

    factory<IVideoPlayerHandler> {
        AndroidVideoPlayerHandler(context = androidContext())
    }

    factory<IInteractor<ExerciseActions, ExerciseDomainModel>>(qualifier = named(name = "exercise")) {
        ExerciseInteractor(repository = get())
    }

    single<IExerciseRepository> {
        ExerciseRepository(api = get<ExerciseApi>(), localSource = get(), json = get())
    }

    factory { ListUiMapper() }

    factory { DetailUiMapper() }

    viewModel {
        ListViewModel(
            exerciseListInteractor = get(qualifier = named(name = "exerciseList")),
            listUiMapper = get(),
            searchInteractor = get(qualifier = named(name = "search"))
        )
    }

    viewModel {
        DetailViewModel(
            savedStateHandle = get(),
            exerciseInteractor = get(qualifier = named(name = "exercise")),
            detailUiMapper = get(),
            playerHandler = get()
        )
    }
}