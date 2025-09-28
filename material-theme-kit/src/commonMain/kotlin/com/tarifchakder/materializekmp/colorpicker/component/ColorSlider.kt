package com.tarifchakder.materializekmp.colorpicker.component

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
import com.tarifchakder.materializekmp.extension.drawTransparentBackground


/**
 * A composable slider that allows the user to select a color from a gradient.
 *
 * The slider displays a gradient of colors and a circular thumb that can be dragged
 * to select a color within the gradient. The selected color is represented as a
 * progress value between 0.0 and 1.0, where 0.0 corresponds to the first color
 * in the list and 1.0 corresponds to the last color.
 *
 * @param colors A list of [Color] objects that define the gradient. The slider will transition
 *               smoothly between these colors. The colors are rendered from left to right.
 * @param selectedColor The currently selected color. This is used to initialize the thumb's
 *                      position when the slider is first displayed.
 * @param onColorChange A callback function that is invoked when the user changes the selected
 *                      color. It provides a Float value representing the progress of the
 *                      selection within the color gradient (0.0 to 1.0).
 */
@Composable
internal fun ColorSlider(
    colors: List<Color>,
    selectedColor: Color,
    onColorChange: (Float) -> Unit
) {

    /**
     * Calculates the initial progress based on the selected color's position in a list of colors.
     *
     * This function determines the initial progress value for a color picker or similar UI element.
     * It does this by finding the index of the `selectedColor` within the provided `colors` list.
     * The progress is then calculated as a normalized value between 0.0 and 1.0,
     * where 0.0 represents the first color in the list, and 1.0 represents the last color.
     *
     * If the `selectedColor` is not found in the `colors` list, the progress defaults to 0.0.
     *
     * @param selectedColor The color that is currently selected.
     * @param colors The list of available colors.
     * @return A float value representing the initial progress, between 0.0 and 1.0 inclusive.
     *         Returns 0.0 if the selected color is not found in the list.
     * @throws IllegalArgumentException if the `colors` list is empty.
     */
    fun calculateInitialProgress(selectedColor: Color, colors: List<Color>): Float {
        val selectedColorIndex = colors.indexOf(selectedColor)
        return if (selectedColorIndex != -1) {
            selectedColorIndex.toFloat() / (colors.size - 1).coerceAtLeast(1)
        } else {
            0f
        }
    }

    var selectionProgress by remember { mutableStateOf(calculateInitialProgress(selectedColor, colors)) }
    var sliderSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(selectionProgress) {
        onColorChange(selectionProgress)
    }

    fun onGestureEvent(touchPosition: Float) {
        val boundedTouchPosition = touchPosition.coerceIn(0f, sliderSize.width.toFloat())
        selectionProgress = (boundedTouchPosition / sliderSize.width).coerceIn(0f, 1f)
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .onSizeChanged { sliderSize = it }
            .pointerInput(Unit) {
                detectTapGestures {
                    onGestureEvent(it.x)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onGestureEvent(change.position.x)
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

        val sliderThumbRadius = size.height / 2.2f
        val sliderThumbCenterX = sliderThumbRadius + ((size.width - sliderThumbRadius * 2) * selectionProgress)

        drawCircle(
            color = Color.White,
            radius = sliderThumbRadius,
            center = Offset(sliderThumbCenterX, (size.height / 2))
        )
    }
}


