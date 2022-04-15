package de.leonfuessner.tada.screens.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.leonfuessner.tada.R
import de.leonfuessner.tada.model.Category
import de.leonfuessner.tada.ui.components.BackgroundCircle
import de.leonfuessner.tada.ui.components.OverviewMoreDropdown
import de.leonfuessner.tada.ui.theme.icons
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@OptIn(InternalCoroutinesApi::class)
@Composable
fun OverviewScreen(
    viewModel: OverviewViewmodel,
    onNavigationRequested: (OverviewContract.SideEffect.Navigation) -> Unit
) {
    val state = viewModel.viewState.value

    LaunchedEffect(viewModel.effect, onNavigationRequested) {
        viewModel.effect.collect {
            when (it) {
                is OverviewContract.SideEffect.Navigation -> onNavigationRequested(it)
            }
        }
    }

    Scaffold(
        content = {
            OverviewContent(
                categories = state.categories,
                sendEvent = { viewModel.setEvent(it) }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setEvent(OverviewContract.Event.AddCategoryClicked) }
            ) {
                Icon(Icons.Default.Add, "add")
            }
        }
    )
}

@Composable
private fun OverviewContent(
    categories: List<Category>,
    sendEvent: (OverviewContract.Event) -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundCircle()
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            OverviewHeader(
                categories
                    .flatMap { it.tasks }
                    .filter { !it.isDone }
                    .size
            )
            Spacer(modifier = Modifier.height(20.dp))
            OverviewList(
                categories = categories,
                sendEvent = sendEvent,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
private fun OverviewHeader(
    taskCount: Int
) {
    Column(
        modifier = Modifier
            .padding(start = 36.dp, top = 20.dp)
            .fillMaxWidth(0.8f)
    ) {
        Text(style = MaterialTheme.typography.h5, text = "Hey there,")
        Text(style = MaterialTheme.typography.body1, text = "Today you have $taskCount task(s)")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OverviewList(
    categories: List<Category>,
    sendEvent: (OverviewContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                sendEvent = sendEvent
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun CategoryItem(
    category: Category,
    sendEvent: (OverviewContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .height(124.dp)
            .fillMaxWidth(0.9f)
            .background(Color.White)
            .clickable {
                sendEvent(OverviewContract.Event.CategoryClicked(category.id))
            },
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
        ) {
            Image(
                painterResource(id = icons[category.imageId]),
                contentDescription = "bathtub",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(

                    style = MaterialTheme.typography.h6,
                    text = category.title
                )
                Text(
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.LightGray,
                    text = "${category.tasks.size} Tasks"
                )
            }

        }

        var showDropDown by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd),
        ) {
            OverviewMoreDropdown(
                expanded = showDropDown,
                onEditClick = { /*TODO*/ },
                onDeleteClick = {
                    sendEvent(OverviewContract.Event.DeleteCategoryClicked(category.id))
                    showDropDown = false
                },
                onDismiss = {
                    showDropDown = false
                }
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            tint = Color.Black,
            contentDescription = "more",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    showDropDown = !showDropDown
                }
        )
    }
}


