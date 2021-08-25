package com.example.tada.screens.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tada.R
import com.example.tada.TadaScreen
import com.example.tada.model.Category

@Composable
fun OverviewScreen(
    viewModel: OverviewViewmodel = hiltViewModel(),
    onCategoryClick: (id: Long) -> Unit
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        content = {
            OverviewContent(
                categories = state.value.categories,
                onCategoryClick = onCategoryClick
            )
        },
    )
}

@Composable
fun OverviewContent(
    categories: List<Category>,
    onCategoryClick: (id: Long) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            OverviewHeader()
            Spacer(modifier = Modifier.height(20.dp))
            OverviewList(
                categories = categories,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@Composable
fun OverviewHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(20.dp)
    ) {
        Text(style = MaterialTheme.typography.h4, text = "Hello there")
        Text(style = MaterialTheme.typography.body1, text = "Today you have 4 tasks")
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverviewList(
    categories: List<Category>,
    onCategoryClick: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
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
    onCategoryClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .height(124.dp)
            .fillMaxWidth(0.9f)
            .clickable { onCategoryClick.invoke(category.id) },
    ) {
        Row(
            modifier = modifier

        ) {
            Column(
                modifier = modifier.align(Alignment.CenterVertically)
            ) {
                Image(
                    painterResource(id = R.drawable.ic_baseline_bathtub_24),
                    contentDescription = "bathtub",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
            Column(
                modifier = modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    style = MaterialTheme.typography.h6,
                    text = category.title
                )
                Text(
                    style = MaterialTheme.typography.body1,
                    text = "${category.tasks.size} Tasks"
                )
            }
            Column(
            ) {
                Image(
                    painterResource(id = R.drawable.ic_baseline_more_vert_24),
                    contentDescription = "more",
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        }
    }


}
