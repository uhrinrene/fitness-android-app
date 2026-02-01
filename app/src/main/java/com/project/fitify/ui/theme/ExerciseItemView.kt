package com.project.fitify.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.fitify.ExercisePackUiModel

@Composable
fun ExerciseItemView(modifier: Modifier = Modifier, model: ExercisePackUiModel) {
    Row(modifier = Modifier.clickable {
        model.onClick()
    }) {
        // TODO spatne funguje
//        thumbnail(imageUrl = model.icon)
        Text(text = model.title)
    }
}