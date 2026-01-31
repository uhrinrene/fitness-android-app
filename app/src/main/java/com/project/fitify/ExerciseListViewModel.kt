package com.project.fitify

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseListViewModel(private val exerciseListInteractor: ExerciseListInteractor) : ViewModel() {

    private val _exerciseList = MutableStateFlow<List<ToolsResponse>>(emptyList())
    val exerciseList = _exerciseList.asStateFlow()

    init {
        initLoad()
    }

    private fun initLoad() {
        viewModelScope.launch {
            exerciseListInteractor.loadData().collect { toolsResponse ->
                when(toolsResponse) {
                    is ResultState.Error -> TODO()
                    ResultState.Loading -> Log.d("TAG", "loading")
                    is ResultState.Success<*> -> Log.d("TAG", "initLoad: ${toolsResponse.value}")
                }
            }
        }
    }
}