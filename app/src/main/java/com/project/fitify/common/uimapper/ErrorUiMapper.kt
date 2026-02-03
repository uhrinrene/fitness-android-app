package com.project.fitify.common.uimapper

import com.project.fitify.ErrorDomainModel
import com.project.fitify.ErrorUiModel
import com.project.fitify.StatefulUiModel

class ErrorUiMapper {

    fun provideErrorState(errorDomainModel: ErrorDomainModel, action: () -> Unit) = StatefulUiModel.Error(
        errorUiModel = ErrorUiModel(
            title = "Vyskytla se chyba",
            message = errorDomainModel.throwable.localizedMessage,
            button = "Zkuste znovu",
            onClick = action
        )
    )

}