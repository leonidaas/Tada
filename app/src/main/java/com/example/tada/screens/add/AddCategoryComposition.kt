package com.example.tada.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.screens.add.AddCategoryViewModel
import com.google.accompanist.insets.imePadding

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onCategoryAdd: () -> Unit = {}
) {
    val name by viewModel.categoryName.collectAsState()

    Column(
        Modifier
            .imePadding()
            .fillMaxWidth()
    ) {
        TextInput(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            name
        ) {
            viewModel.onCategoryTitleChange(it)
        }
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(32.dp),
            onClick = {
                viewModel.addCategory(name)
                onCategoryAdd.invoke()
            }
        ) {
            Text("Add category")
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier,
    text: String,
    onCategoryTitleChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onCategoryTitleChange
    )
}