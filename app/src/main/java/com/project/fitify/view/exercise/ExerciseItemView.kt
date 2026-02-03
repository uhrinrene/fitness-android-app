package com.project.fitify.view.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.project.fitify.view.ThumbnailView

@Composable
fun ExerciseItemView(modifier: Modifier = Modifier, model: ExerciseUiModel) {

    val itemShape = RoundedCornerShape(12.dp)

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 4.dp)
        .clip(itemShape)
        .clickable {
            model.onClick()
        }, verticalAlignment = Alignment.CenterVertically) {
        ThumbnailView(imageUrl = model.icon)
        Text(modifier = Modifier.padding(horizontal = 16.dp), text = model.title)
    }
}