package com.project.fitify.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.project.fitify.ErrorUiModel
import com.project.fitify.StatefulUiModel

@Composable
fun <T> StatefulView(
    data: StatefulUiModel<T>,
    modifier: Modifier = Modifier,
    loading: @Composable () -> Unit = { LoadingView() },
    error: @Composable (ErrorUiModel) -> Unit = { ErrorView(errorUiModel = it) },
    content: @Composable (T) -> Unit,
) {
    AnimatedContent(modifier = modifier, targetState = data, label = "stateful-content", contentKey = { it::class }) {
        when (it) {
            is StatefulUiModel.Error -> error(it.errorUiModel)
            is StatefulUiModel.Content<T> -> content(it.data)
            is StatefulUiModel.Loading<T> -> loading()
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = modifier,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorUiModel: ErrorUiModel
) {
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = errorUiModel.title)

        if (errorUiModel.message != null) {
            Text(text = errorUiModel.message)
        }

        Button(onClick = errorUiModel.onClick) {
            Text(text = errorUiModel.button)
        }

    }
}