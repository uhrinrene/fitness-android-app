package com.project.fitify

import kotlinx.coroutines.flow.Flow

interface ISimpleInteractor : IInteractor {

    fun loadData(): Flow<ResultState>
}