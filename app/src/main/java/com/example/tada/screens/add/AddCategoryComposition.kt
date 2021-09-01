package com.example.tada.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.screens.add.AddCategoryViewModel
import com.google.accompanist.insets.imePadding

@ExperimentalMaterialApi
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
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary
        ),
        value = text,
        onValueChange = onCategoryTitleChange
    )
}