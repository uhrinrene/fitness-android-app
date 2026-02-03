package com.project.fitify.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.project.fitify.viewmodel.list.uimapping.SearchUiModel

@Composable
fun SearchBarView(
    model: SearchUiModel,
    modifier: Modifier = Modifier
) {

    var textValue by rememberSaveable(model.value) {
        mutableStateOf(model.value)
    }

    OutlinedTextField(
        value = textValue,
        onValueChange = { value ->
            textValue = value
            model.onValueChange(textValue)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = {
            Text(text = model.hint, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.tertiary)
        },
        leadingIcon = {
            Icon(modifier = Modifier.size(size = 40.dp), painter = painterResource(id = model.leadingIcon), contentDescription = null, tint = MaterialTheme.colorScheme.surface)
        },
        shape = RoundedCornerShape(size = 28.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}