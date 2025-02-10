package com.tarif.dynamictheme.colorpicker.pickers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tarif.dynamictheme.extension.drawTransparentBackground
import io.github.mohammedalaamorsi.colorpicker.helper.ColorPickerHelper.calculateInitialProgress

@Composable
internal fun ColorSlider(
    colors: List<Color>,
    initialColor: Color,
    onProgress: (Float) -> Unit
) {
    var progress by remember { mutableStateOf(calculateInitialProgress(initialColor, colors)) }
    var slideBarSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(progress) {
        onProgress(progress)
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .onSizeChanged {
                slideBarSize = it
            }
            .pointerInput(Unit){
                detectTapGestures {
                    val xPosition = it.x.coerceIn(0f, slideBarSize.width.toFloat())
                    progress = (xPosition / slideBarSize.width).coerceIn(0f, 1.5f)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val xPosition = change.position.x.coerceIn(0f, slideBarSize.width.toFloat())
                    progress = (xPosition / slideBarSize.width).coerceIn(0f, 1.5f)
                }
            }
            .clip(RoundedCornerShape(100))
    ) {
        drawTransparentBackground(3)
        drawRect(
            brush = Brush.horizontalGradient(
                colors = colors,
                startX = size.height / 2,
                endX = size.width - size.height / 2
            )
        )

        val thumbRadius = size.height / 2.2f
        val thumbCenterX = thumbRadius + ((size.width - thumbRadius * 2) * progress)

        drawCircle(
            color = Color.White,
            radius = thumbRadius,
            center = Offset(thumbCenterX, (size.height / 2))
        )
    }
}

