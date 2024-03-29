package de.leonfuessner.tada.screens.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    onNavigationRequested: (AddCategoryContract.SideEffect.Navigation) -> Unit
) {
    LaunchedEffect(viewModel.effect, onNavigationRequested) {
        viewModel.effect.collect {
            when (it) {
                is AddCategoryContract.SideEffect.Navigation.ToOverview -> onNavigationRequested(it)
            }
        }
    }

    val state = viewModel.viewState.value
    AddCategoryContent(
        text = state.text,
        id = state.imagePosition,
        sendEvent = {
            viewModel.setEvent(it)
        })

}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun AddCategoryContent(
    text: String,
    id: Int,
    modifier: Modifier = Modifier,
    sendEvent: (AddCategoryContract.Event) -> Unit
) {
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
            sendEvent(it)
        }

        TextInput(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = text
        ) {
            sendEvent(it)
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
                sendEvent(AddCategoryContract.Event.CategoryAdded)
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
    sendEvent: (AddCategoryContract.Event) -> Unit,
) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = images.size,
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState
    ) { page ->
        sendEvent(AddCategoryContract.Event.ImageChanged(pagerState.currentPage))
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
    sendEvent: (AddCategoryContract.Event) -> Unit
) {
    OutlinedTextField(
        placeholder = { if (text.isEmpty()) Text("Title") else Text("") },
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary
        ),
        value = text,
        onValueChange = {
            sendEvent(AddCategoryContract.Event.TextChanged(it))
        }
    )
}