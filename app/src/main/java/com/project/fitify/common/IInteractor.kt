package com.project.fitify.common

import com.project.fitify.ResultState
import kotlinx.coroutines.flow.Flow

interface IInteractor<Action, Output> {

    fun loadData(): Flow<ResultState<Output>>

    suspend fun sendAction(action: Action)
}