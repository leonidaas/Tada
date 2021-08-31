package com.example.tada.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tada.model.Category
import com.example.tada.model.Task
import com.example.tada.ui.components.BackgroundCircle
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    categoryId: String,
    viewModel: DetailViewModel = hiltViewModel(),
    onAddButtonClick: () -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {

    viewModel.initalize(categoryId)

    val category by viewModel.tasks.collectAsState()

    Scaffold(
        content = {
            DetailContent(
                category,
                onCheckChange = { id, checked ->
                    viewModel.onCheckChange(id, checked)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddButtonClick
            ) {
                Icon(Icons.Default.Add, "add")
            }
        }
    )
}

@Composable
fun DetailContent(
    category: Category?,
    onCheckChange: (String, Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundCircle()

        Column {


            TaskList(
                modifier = Modifier.fillMaxSize(),
                tasks = category?.tasks,
                onCheckChange = onCheckChange
            )
        }
    }
}

@Composable
fun TaskHeader() {



}

@Composable
fun TaskList(
    modifier: Modifier,
    tasks: List<Task>? = emptyList(),
    onCheckChange: (String, Boolean) -> Unit
) {
    if (tasks != null) {
        LazyColumn(
            modifier
                .fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskItem(task, onCheckChange)
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckChange: (String, Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = {
                onCheckChange.invoke(task.id, it)
            }
        )

        Text(text = task.text)
    }

}