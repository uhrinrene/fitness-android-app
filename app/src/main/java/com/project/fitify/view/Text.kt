package com.project.fitify.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.project.fitify.ui.theme.RobotoFontFamily

@Composable
fun FontText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily = RobotoFontFamily,
    fontWeight: FontWeight? = null
) {

    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}