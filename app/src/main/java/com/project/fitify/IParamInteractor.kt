package com.project.fitify

import kotlinx.coroutines.flow.Flow

interface IParamInteractor<Input, Output> : IInteractor {

    fun loadData(arg: Input): Flow<ResultState<Output>>
}