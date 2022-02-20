package de.leonfuessner.tada.screens.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.imePadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import de.leonfuessner.tada.ui.theme.icons
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onCategoryAdd: () -> Unit = {}
) {
    val name by viewModel.categoryName.collectAsState()
    val categoryImageId by viewModel.categoryImageId.collectAsState()

    Column(
        Modifier
            .imePadding()
            .fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Choose your weapon"
        )

        ImageSelection(
            images = icons,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            viewModel.onCategoryImageChange(it)
        }

        TextInput(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = name
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
                viewModel.addCategory(categoryImageId, name)
                onCategoryAdd.invoke()
            }
        ) {
            Text("Add category")
        }
    }


}

@ExperimentalPagerApi
@Composable
fun ImageSelection(
    images: List<Int>,
    modifier: Modifier,
    onImageSelectionChange: (Int) -> Unit,
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = images.size,
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState
    ) { page ->
        onImageSelectionChange.invoke(pagerState.currentPage)
        Card(
            elevation = 0.dp,
            modifier = Modifier
                .border(BorderStroke(0.dp, Color.Transparent))
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = ScaleFactor(0.5f, 0.5f),
                        stop = ScaleFactor(1f, 1f),
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale.scaleX
                        scaleY = scale.scaleY
                    }

                    alpha = lerp(
                        start = ScaleFactor(0.5f, 0.5f),
                        stop = ScaleFactor(1f, 1f),
                        1f - pageOffset.coerceIn(0f, 1f)
                    ).scaleX
                }
                .fillMaxWidth(0.4f)
        ) {
            Image(
                painterResource(id = images[page]),
                contentDescription = "bathtub",
                modifier = Modifier
                    .size(48.dp)
                    .offset {
                        val pageOffset =
                            this@HorizontalPager.calculateCurrentOffsetForPage(page)
                        // Then use it as a multiplier to apply an offset
                        IntOffset(
                            x = (36.dp * pageOffset).roundToPx(),
                            y = 0
                        )
                    }

            )
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
        placeholder = { if (text.isEmpty()) Text("Title") else Text("") },
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary
        ),
        value = text,
        onValueChange = onCategoryTitleChange
    )
}