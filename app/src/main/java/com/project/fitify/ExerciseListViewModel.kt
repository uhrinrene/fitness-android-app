package com.project.fitify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseListInteractor: IInteractor<ExercisePacksActions, ExercisePacksDomainModel>,
    private val exerciseListUiMapper: ExerciseListUiMapper,
    private val searchInteractor: IInteractor<SearchActions, ExercisePacksDomainModel>
) : ViewModel() {

    private val _exerciseList =
        MutableStateFlow<StatefulModel<ExercisePacksUiModel>>(value = StatefulModel.Loading())
    val exerciseList = _exerciseList.asStateFlow()

    init {
        exerciseListCollect()
        // TODO mozna vlastni metoda?
        viewModelScope.launch { exerciseListInteractor.sendAction(action = ExercisePacksActions.LoadData) }
    }

    private fun exerciseListCollect() = viewModelScope.launch {
        merge(
            exerciseListInteractor.loadData(),
            searchInteractor.loadData()
        ).collect { resultState ->
            when (resultState) {
                is ResultState.Error -> {
                    _exerciseList.emit(
                        value = StatefulModel.Error(
                            errorModel = ErrorModel(
                                title = "error",
                                button = "button"
                            )
                        )
                    )
                    Log.d("TAG", "Error" + resultState.errorDomainModel.message)
                }

                ResultState.Loading -> {
                    _exerciseList.emit(value = StatefulModel.Loading())
                    Log.d("TAG", "Loading")
                }

                is ResultState.Success<ExercisePacksDomainModel> -> _exerciseList.emit(
                    value = exerciseListUiMapper.provideContentState(
                        domainModel = resultState.value
                    )
                )
            }
        }
    }
}