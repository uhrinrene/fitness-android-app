package com.project.fitify

import kotlinx.coroutines.flow.Flow

interface ISimpleInteractor<Output> : IInteractor {

    fun loadData(): Flow<ResultState<Output>>
}