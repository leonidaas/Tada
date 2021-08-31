package com.example.tada.screens.overview

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tada.R
import com.example.tada.model.Category
import com.example.tada.ui.components.BackgroundCircle
import com.example.tada.ui.theme.icons
import com.example.tada.util.getMatchingIcon
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun OverviewScreen(
    viewModel: OverviewViewmodel = hiltViewModel(),
    onCategoryClick: (id: String) -> Unit = {},
    onAddCategoryClick: () -> Unit = {}
) {
//    val state by viewModel.state.collectAsState()
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        content = {
            OverviewContent(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCategoryClick
            ) {
                Icon(Icons.Default.Add, "add")
            }
        }
    )
}

@Composable
fun OverviewContent(
    categories: List<Category>,
    onCategoryClick: (id: String) -> Unit
) {

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundCircle()
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            OverviewHeader()
            Spacer(modifier = Modifier.height(20.dp))
            OverviewList(
                categories = categories,
                onCategoryClick = onCategoryClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun OverviewHeader() {
    Column(
        modifier = Modifier
            .padding(start = 36.dp, top = 20.dp)
            .fillMaxWidth(0.8f)
    ) {
        Text(style = MaterialTheme.typography.h5, text = "Hey there")
        Text(style = MaterialTheme.typography.body1, text = "Today you have 4 tasks")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverviewList(
    categories: List<Category>,
    onCategoryClick: (id: String) -> Unit,
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
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CategoryItem(
    category: Category,
    onCategoryClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .height(124.dp)
            .fillMaxWidth(0.9f)
            .background(Color.White)
            .clickable { onCategoryClick.invoke(category.id) },
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart)
        ) {
            Image(
                painterResource(id = getMatchingIcon(category.id)),
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

        Image(
            painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = "more",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(Alignment.TopEnd)
        )
    }
}


