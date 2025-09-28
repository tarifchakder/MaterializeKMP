package com.tarifchakder.materializekmp.colorpicker.pickers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.tarifchakder.materializekmp.colorpicker.component.AlphaColorBar
import com.tarifchakder.materializekmp.colorpicker.component.ColorSlider
import com.tarifchakder.materializekmp.colorpicker.model.ColorRange
import com.tarifchakder.materializekmp.colorpicker.model.Colors.gradientColors
import com.tarifchakder.materializekmp.extension.blue
import com.tarifchakder.materializekmp.extension.calculateRangeProgress
import com.tarifchakder.materializekmp.extension.darken
import com.tarifchakder.materializekmp.extension.drawColorSelector
import com.tarifchakder.materializekmp.extension.green
import com.tarifchakder.materializekmp.extension.lighten
import com.tarifchakder.materializekmp.extension.red
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@Composable
internal fun ClassicColorPicker(
    modifier: Modifier = Modifier,
    pickerSize: Dp,
    isAlphaSliderVisible: Boolean,
    defaultColor: Color,
    onNewColor: (Color) -> Unit
) {
    val initialPickerOffset = pickerSize.value / 2f
    var pickerPosition by remember { mutableStateOf(Offset(initialPickerOffset, initialPickerOffset)) }
    var pickerSizeInPixels by remember { mutableStateOf(IntSize(1, 1)) }
    val alphaValue = remember { mutableStateOf(defaultColor.alpha) }
    var baseColor by remember { mutableStateOf(defaultColor) }
    var currentColor by remember { mutableStateOf(defaultColor) }

    fun calculateColorFromPickerPosition(
        baseColor: Color,
        pickerPosition: Offset,
        pickerSizeInPixels: IntSize,
        alphaValue: Float
    ): Color {
        if (pickerSizeInPixels == IntSize.Zero) return baseColor
        val horizontalProgress = (1f - (pickerPosition.x / pickerSizeInPixels.width)).coerceIn(0f, 1f)
        val verticalProgress = (pickerPosition.y / pickerSizeInPixels.height).coerceIn(0f, 1f)

        return Color(
            red= baseColor.red().lighten(horizontalProgress).darken(verticalProgress),
            green = baseColor.green().lighten(horizontalProgress).darken(verticalProgress),
            blue = baseColor.blue().lighten(horizontalProgress).darken(verticalProgress),
            alpha = alphaValue.roundToInt()
        )
    }

    LaunchedEffect(baseColor, pickerPosition, pickerSizeInPixels, alphaValue) {
        currentColor = calculateColorFromPickerPosition(baseColor, pickerPosition, pickerSizeInPixels, (255 * alphaValue.value))
    }

    LaunchedEffect(currentColor) {
        onNewColor(currentColor)
    }

    fun handleGestureEvent(touchX: Float, touchY: Float) {
        pickerPosition = Offset(
            touchX.coerceIn(0f, pickerSizeInPixels.width.toFloat()),
            touchY.coerceIn(0f, pickerSizeInPixels.height.toFloat())
        )
    }

    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Box(
            modifier = modifier
                .onSizeChanged { pickerSizeInPixels = it }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        handleGestureEvent(offset.x, offset.y)
                    }
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, _, _, _ ->
                        handleGestureEvent(centroid.x, centroid.y)
                    }
                }.size(pickerSize)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                drawRect(Brush.horizontalGradient(listOf(Color.White, baseColor)))
                drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                this.drawColorSelector(currentColor, pickerPosition)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ColorSlider(
            colors = gradientColors,
            selectedColor = defaultColor
        ) { sliderValue ->
            val (rangeProgress, range) = calculateRangeProgress(sliderValue.toDouble())
            val (redValue, greenValue, blueValue) = when (range) {
                ColorRange.RedToYellow -> Triple(255, (255 * rangeProgress).roundToInt(), 0)
                ColorRange.YellowToGreen -> Triple((255 * (1 - rangeProgress)).roundToInt(), 255, 0)
                ColorRange.GreenToCyan -> Triple(0, 255, (255 * rangeProgress).roundToInt())
                ColorRange.CyanToBlue -> Triple(0, (255 * (1 - rangeProgress)).roundToInt(), 255)
                ColorRange.BlueToMagenta -> Triple((255 * rangeProgress).roundToInt(), 0, 255)
                ColorRange.MagentaToRed -> Triple(255, 0, (255 * (1 - rangeProgress)).roundToInt())
            }
            baseColor = Color(redValue, greenValue, blueValue)
        }

        AlphaColorBar(isAlphaSliderVisible, baseColor, defaultColor, alphaValue)

    }
}
