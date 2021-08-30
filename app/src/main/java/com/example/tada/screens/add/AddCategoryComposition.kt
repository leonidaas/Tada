package com.example.tada.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.screens.add.AddCategoryViewModel
import java.lang.reflect.Modifier

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onCategoryAdd: () -> Unit = {}
) {
    val name by viewModel.categoryName.collectAsState()

    Column {
        TextInput(
            name
        ) {
            viewModel.onCategoryTitleChange(it)
        }
        Button(onClick = {
            viewModel.addCategory(name)
            onCategoryAdd.invoke()
        }) {
            Text("Hallo Welt")
        }
    }
}

@Composable
fun TextInput(
    text: String,
    onCategoryTitleChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onCategoryTitleChange
    )
}