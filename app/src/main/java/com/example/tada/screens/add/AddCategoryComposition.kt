package com.example.tada.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.screens.add.AddCategoryViewModel

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onCategoryAdd: () -> Unit = {}
) {

    Column {
        Button(onClick = {
            viewModel.addCategory("test")
            onCategoryAdd.invoke()
        }) {
            Text("Hallo Welt")
        }
    }


}