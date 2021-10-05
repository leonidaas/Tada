package de.leonfuessner.tada.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.leonfuessner.tada.R
import com.google.accompanist.insets.imePadding
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.model.Task
import de.leonfuessner.tada.ui.components.BackgroundCircle
import de.leonfuessner.tada.ui.components.DetailDropdown
import de.leonfuessner.tada.ui.theme.icons

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigateUp: () -> Unit = {}
) {

    val category by viewModel.categoryFlow.collectAsState()
    val isEditMode by viewModel.isInAddMode.collectAsState()
    val taskText by viewModel.taskText.collectAsState()
    val shouldShowDropdown by viewModel.dropdownIsShown.collectAsState()

    if (category != null) {
        Scaffold(
            content = {
                DetailContent(
                    category = category!!,
                    isEditMode = isEditMode,
                    taskText = taskText,
                    expandedDropdown = shouldShowDropdown,
                    onCheckChange = { id, checked ->
                        viewModel.onCheckChange(id, checked)
                    },
                    showDropdown = { viewModel.showDropdown(it) },
                    onNavigateUp = onNavigateUp,
                    onEditModeChange = { viewModel.switchAddMode(it) },
                    onTextChange = { viewModel.onTextChange(it) },
                    onEnterClick = { viewModel.addTask() },
                    onDoneRemoveClick = { viewModel.removeFinishedTasks(viewModel.categoryId) },
                    onRemoveAllClick = { viewModel.removeAllTasks(viewModel.categoryId) }
                )
            }
        )
    }

}


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun DetailContent(
    category: Category,
    isEditMode: Boolean,
    taskText: String,
    expandedDropdown: Boolean,
    onCheckChange: (Task, Boolean) -> Unit,
    onNavigateUp: () -> Unit = {},
    showDropdown: (Boolean) -> Unit = {},
    onEditModeChange: (Boolean) -> Unit,
    onTextChange: (text: String) -> Unit,
    onEnterClick: () -> Unit,
    onDoneRemoveClick: () -> Unit,
    onRemoveAllClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BackgroundCircle()
            Column(
                modifier = Modifier
                    .imePadding()
                    .padding(32.dp)
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
                            .clickable { showDropdown(true) }
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                    ) {
                        DetailDropdown(
                            expanded = expandedDropdown,
                            onDoneRemoveClick = onDoneRemoveClick,
                            onRemoveAllClick = onRemoveAllClick,
                            onDismiss = { showDropdown(false) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
                TaskHeader(
                    category = category
                )
                Spacer(modifier = Modifier.height(32.dp))
                TaskList(
                    modifier = Modifier
                        .padding(bottom = 64.dp)
                        .align(Alignment.Start),
                    tasks = category.tasks,
                    onCheckChange = onCheckChange,
                )
            }


            if (!isEditMode) {
                EditModeItem(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    onEditModeChange = onEditModeChange
                )
            } else {
                AnimatedVisibility(
                    visible = isEditMode,
                    enter = slideIn(initialOffset = { IntOffset(0, it.height) }),
                    exit = slideOut(targetOffset = { IntOffset(0, it.height) }),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    TaskAddBar(
                        value = taskText,
                        modifier = Modifier.padding(bottom = 16.dp),
                        onValueChange = onTextChange,
                        onAddAction = onEnterClick
                    )
                }
            }
        }
    }
}

@Composable
fun TaskHeader(
    category: Category
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = icons[category.imageId]),
            contentDescription = "bathtub",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                style = MaterialTheme.typography.h4,
                text = category.title ?: ""
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.subtitle2,
                color = Color.LightGray,
                text = "${category.tasks.size} Tasks"
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun TaskList(
    modifier: Modifier,
    tasks: List<Task>? = emptyList(),
    onCheckChange: (Task, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(tasks ?: emptyList()) { task ->
                TaskItem(
                    task,
                    onCheckChange
                )
            }
        }
    }

}

@Composable
fun TaskItem(
    task: Task,
    onCheckChange: (Task, Boolean) -> Unit
) {
    Column {
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
        Spacer(Modifier.height(8.dp))
        Divider(color = Color.Gray)
    }

}

@Composable
fun EditModeItem(
    modifier: Modifier = Modifier,
    onEditModeChange: (Boolean) -> Unit
) {

    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "bac",
        modifier = modifier
            .size(32.dp)
            .clickable { onEditModeChange(true) }
    )

}