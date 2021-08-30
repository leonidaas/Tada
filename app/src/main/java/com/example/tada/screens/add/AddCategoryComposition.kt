package com.example.tada.screens

import android.widget.EditText
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.screens.add.AddCategoryViewModel
import com.google.android.material.textfield.TextInputEditText

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

@Composable
fun TextInput() {
    OutlinedTextField(
        value =, onValueChange =
    )
}