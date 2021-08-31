package com.example.tada.screens.detail

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tada.R
import com.example.tada.model.Category
import com.example.tada.model.Task
import com.example.tada.ui.components.BackgroundCircle
import com.example.tada.ui.theme.icons
import com.example.tada.util.getMatchingIcon
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

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
                category = category,
                onCheckChange = { id, checked ->
                    viewModel.onCheckChange(id, checked)
                },
                onNavigateUp = onNavigateUp
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
    onCheckChange: (Task, Boolean) -> Unit,
    onNavigateUp: () -> Unit = {},
    onMoreButtonClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundCircle()
        Column(
            modifier = Modifier.padding(32.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "bac",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onNavigateUp() }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "bac",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { onMoreButtonClick() }
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            TaskHeader(
                category = category
            )
            Spacer(modifier = Modifier.height(32.dp))
            TaskList(
                modifier = Modifier
                    .align(Alignment.Start),
                tasks = category?.tasks,
                onCheckChange = onCheckChange
            )
        }
    }
}

@Composable
fun TaskHeader(
    category: Category?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = getMatchingIcon(category?.id ?: "")),
            contentDescription = "bathtub",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                style = MaterialTheme.typography.caption,
                text = "${category?.tasks?.size} Tasks"
            )
            Text(
                style = MaterialTheme.typography.h4,
                text = category?.title ?: ""
            )
        }
    }

}

@Composable
fun TaskList(
    modifier: Modifier,
    tasks: List<Task>? = emptyList(),
    onCheckChange: (Task, Boolean) -> Unit
) {
    if (tasks != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task,
                    onCheckChange
                )
                Spacer(Modifier.height(8.dp))
                Divider(color = Color.Gray)
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckChange: (Task, Boolean) -> Unit
) {
    Row {
        Spacer(modifier = Modifier.width(16.dp))
        Checkbox(
            checked = task.isDone,
            onCheckedChange = {
                onCheckChange.invoke(task, it)
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            style = MaterialTheme.typography.body1,
            text = task.text,
            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
        )
    }

}