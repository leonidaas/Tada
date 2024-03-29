package de.leonfuessner.tada.screens.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TaskAddBar(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (input: String) -> Unit = {},
    onAddAction: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        placeholder = { if (value.isEmpty()) Text("Task") else Text("") },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onAddAction() }
        ),
        value = value,
        onValueChange = onValueChange,
        singleLine = true
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {}
    }
}