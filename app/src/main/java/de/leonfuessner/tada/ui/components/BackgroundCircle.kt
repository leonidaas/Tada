package de.leonfuessner.tada.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundCircle() {
    val color = MaterialTheme.colors.primary
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height
            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(-canvasWidth / 2, -canvasHeight / 2),
                size = Size(canvasWidth * 2, canvasHeight)
            )
        }
    )
}