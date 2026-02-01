package com.project.fitify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseListInteractor: ISimpleInteractor<ExercisePacksDomainModel>,
    private val exerciseListUiMapper: ExerciseListUiMapper,
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private val _exerciseList = MutableStateFlow<StatefulModel<ExercisePacksUiModel>>(value = StatefulModel.Loading())
    val exerciseList = _exerciseList.asStateFlow()

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            exerciseListInteractor.loadData().collect { resultState ->
                when (resultState) {
                    is ResultState.Error -> {
                        _exerciseList.emit(value = StatefulModel.Error(errorModel = ErrorModel(title = "error", button = "button")))
                        Log.d("TAG", "Error" + resultState.errorDomainModel.message)
                    }
                    ResultState.Loading -> {
                        _exerciseList.emit(value = StatefulModel.Loading())
                        Log.d("TAG", "Loading")
                    }
                    is ResultState.Success<ExercisePacksDomainModel> -> _exerciseList.emit(value = exerciseListUiMapper.provideContentState(domainModel = resultState.value))
                }
            }
        }
    }
}