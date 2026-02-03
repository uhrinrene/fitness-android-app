package com.project.fitify.common.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

fun <T> safeFlow(
    action: suspend FlowCollector<T>.(CoroutineScope) -> Unit,
    catchAction: suspend FlowCollector<T>.(Throwable) -> Unit,
): Flow<T> {
    return flow {
        coroutineScope {
            action(this)
        }
    }.catch(catchAction)
}