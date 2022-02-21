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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import de.leonfuessner.tada.R
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.model.Task
import de.leonfuessner.tada.ui.components.BackgroundCircle
import de.leonfuessner.tada.ui.components.DetailDropdown
import de.leonfuessner.tada.ui.theme.icons
import kotlinx.coroutines.flow.collect

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigationRequested: (DetailContract.SideEffect.Navigation) -> Unit = {}
) {
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel.effect, onNavigationRequested) {
        viewModel.effect.collect {
            when (it) {
                is DetailContract.SideEffect.Navigation.ToOverview -> {
                    onNavigationRequested(it)
                }
            }
        }
    }

    if (state.category == null) {
        CircularProgressIndicator()
    } else {
        Scaffold(
            content = {
                DetailContent(
                    category = state.category,
                    isEditMode = state.isEditMode,
                    expandedDropdown = state.shouldShowDropdown,
                    sendEvent = { viewModel.setEvent(it) }
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
    expandedDropdown: Boolean,
    sendEvent: (DetailContract.Event) -> Unit
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
                            .clickable { sendEvent(DetailContract.Event.NavigateUpClicked) }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                        contentDescription = "bac",
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterEnd)
                            .clickable { sendEvent(DetailContract.Event.MoreButtonClicked) }
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                    ) {
                        DetailDropdown(
                            expanded = expandedDropdown,
                            onDoneRemoveClick = { sendEvent(DetailContract.Event.RemoveDoneTasksClicked) },
                            onRemoveAllClick = { sendEvent(DetailContract.Event.RemoveAllTasksClicked) },
                            onDismiss = { sendEvent(DetailContract.Event.DismissClicked) }
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
                    sendEvent
                )
            }


            if (!isEditMode) {
                EditModeItem(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    onEditModeChange = { sendEvent(DetailContract.Event.EditModeChanged) }
                )
            } else {
                var text by remember {
                    mutableStateOf("")
                }

                AnimatedVisibility(
                    visible = isEditMode,
                    enter = slideIn(initialOffset = { IntOffset(0, it.height) }),
                    exit = slideOut(targetOffset = { IntOffset(0, it.height) }),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    TaskAddBar(
                        value = text,
                        modifier = Modifier.padding(bottom = 16.dp),
                        onValueChange = { text = it },
                        onAddAction = {
                            sendEvent(DetailContract.Event.ItemAdded(text))
                        }
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
    sendEvent: (DetailContract.Event) -> Unit
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
                    sendEvent
                )
            }
        }
    }

}

@Composable
fun TaskItem(
    task: Task,
    sendEvent: (DetailContract.Event) -> Unit
) {
    Column {
        Row {
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(
                checked = task.isDone,
                onCheckedChange = {
                    sendEvent(DetailContract.Event.OnCheckClicked(task, it))
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