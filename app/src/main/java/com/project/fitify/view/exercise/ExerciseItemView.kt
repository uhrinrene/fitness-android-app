package com.project.fitify.view.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.fitify.view.ThumbnailView

@Composable
fun ExerciseItemView(modifier: Modifier = Modifier, model: ExerciseUiModel) {
    Row(modifier = modifier.clickable {
        model.onClick()
    }) {
        ThumbnailView(imageUrl = model.icon)
        Text(text = model.title)
    }
}