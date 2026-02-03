package com.project.fitify

import com.project.fitify.common.coroutines.safeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

sealed class ResultState<out T> {
    data class Success<out T : Any>(val value: T) : ResultState<T>()
    data object Loading : ResultState<Nothing>()
    data class Error(val errorDomainModel: ErrorDomainModel) : ResultState<Nothing>()
}

data class ErrorDomainModel(val throwable: Throwable, val message: String? = null)

fun <T : Any> safeFlowResultState(
    action: suspend FlowCollector<ResultState<T>>.(CoroutineScope) -> Unit,
) = safeFlow(
    catchAction = { exception ->
        emit(ResultState.Error(errorDomainModel = ErrorDomainModel(throwable = exception)))
    }, action = action
)

@OptIn(FlowPreview::class)
fun <T : Any> loadResultState(
    load: suspend (CoroutineScope) -> T,
) = safeFlowResultState { scope ->
    emit(value = ResultState.Loading)
    emit(value = ResultState.Success(value = load(scope)))
}

@Suppress("UNCHECKED_CAST")
suspend fun <R1, T : ResultState<R1>> Flow<T>.collectEmits(
    actionLoading: (suspend () -> Unit),
    actionError: (suspend (errorDomainModel: ErrorDomainModel) -> Unit?)? = null,
    actionSuccess: (suspend (value: R1) -> Unit)? = null,
) = collect { value ->
    when (value) {
        is ResultState.Loading -> actionLoading()
        is ResultState.Success<*> -> actionSuccess?.invoke(value.value as R1)
        is ResultState.Error -> actionError?.let {
            it(value.errorDomainModel)
        }
    }
}