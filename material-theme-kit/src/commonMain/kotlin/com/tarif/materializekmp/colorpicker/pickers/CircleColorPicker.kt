package com.tarif.materializekmp.colorpicker.pickers


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tarif.materializekmp.colorpicker.component.AlphaColorBar
import com.tarif.materializekmp.colorpicker.component.ColorSlider
import com.tarif.materializekmp.colorpicker.helper.BoundedPointStrategy
import com.tarif.materializekmp.colorpicker.helper.MathHelper.getBoundedPointWithInRadius
import com.tarif.materializekmp.colorpicker.helper.MathHelper.getLength
import com.tarif.materializekmp.colorpicker.model.ColorRange
import com.tarif.materializekmp.colorpicker.model.Colors.gradientColors
import com.tarif.materializekmp.extension.blue
import com.tarif.materializekmp.extension.calculateRangeProgress
import com.tarif.materializekmp.extension.colorPickerCenterColor
import com.tarif.materializekmp.extension.drawColorSelector
import com.tarif.materializekmp.extension.green
import com.tarif.materializekmp.extension.moveColorTo
import com.tarif.materializekmp.extension.red
import com.tarif.materializekmp.extension.toDegrees
import kotlin.math.atan2
import kotlin.math.roundToInt

/**
 * A composable function that displays a circular color picker.
 *
 * This picker allows the user to select a color from a circular gradient. It supports
 * customization of size, alpha channel visibility, brightness adjustment, and inverting the center color.
 *
 * @param modifier Modifier for styling the color picker.
 * @param pickerSize The size (width and height) of the circular color picker.
 * @param showAlpha Whether to show the alpha (transparency) control.
 * @param showBrightness Whether to show the brightness slider.
 * @param invertCenter Whether to invert the center color of the picker. When true, the center is black when brightness is 1 and white when brightness is 0, otherwise it will be white when brightness is 1 and black when brightness is 0.
 * @param initialColor The initial color of the picker. Defaults to red. */
@Composable
internal fun CircleColorPicker(
    modifier: Modifier = Modifier,
    pickerSize: Dp,
    showAlpha: Boolean,
    showBrightness: Boolean,
    invertCenter: Boolean,
    initialColor: Color = Color.Red,
    onColorChange: (Color) -> Unit
) {
    val initialPickerOffset = pickerSize.value / 2f
    var radius by remember { mutableStateOf(0f) }
    var pointerOffset by remember { mutableStateOf(Offset(initialPickerOffset,initialPickerOffset)) }
    var currentColor by remember { mutableStateOf(initialColor) }
    var brightness by remember { mutableStateOf(1f) }
    val currentAlpha = remember { mutableStateOf(initialColor.alpha) }

    LaunchedEffect(brightness, currentColor, currentAlpha) {
            onColorChange(
                Color(
                    currentColor.red().moveColorTo(!invertCenter, brightness),
                    currentColor.green().moveColorTo(!invertCenter, brightness),
                    currentColor.blue().moveColorTo(!invertCenter, brightness),
                    (255 * currentAlpha.value).roundToInt()
                )
            )
    }

    fun onGestureEvent(horizontal: Float, vertical: Float) {
        val colorWheelAngle = (toDegrees(atan2(vertical - radius, horizontal - radius).toDouble()) + 360) % 360
        val distanceToCenter = getLength(horizontal, vertical, radius)
        val radialProgress = 1 - (distanceToCenter / radius).coerceIn(0f, 1f)
        val angularProgress = colorWheelAngle / 360f
        val (rangeProgress, range) = calculateRangeProgress(angularProgress)

        currentColor = when (range) {
            ColorRange.RedToYellow -> {
                Color(
                    red = 255.moveColorTo(invertCenter, radialProgress),
                    green = (255f * rangeProgress).moveColorTo(invertCenter, radialProgress).roundToInt(),
                    blue = 0.moveColorTo(invertCenter, radialProgress),
                )
            }

            ColorRange.YellowToGreen -> {
                Color(
                    red = (255 * (1 - rangeProgress)).moveColorTo(invertCenter, radialProgress).roundToInt(),
                    green = 255.moveColorTo(invertCenter, radialProgress),
                    blue = 0.moveColorTo(invertCenter, radialProgress),
                )
            }

            ColorRange.GreenToCyan -> {
                Color(
                    red = 0.moveColorTo(invertCenter, radialProgress),
                    green = 255.moveColorTo(invertCenter, radialProgress),
                    blue = (255 * rangeProgress).moveColorTo(invertCenter, radialProgress).roundToInt(),
                )
            }

            ColorRange.CyanToBlue -> {
                Color(
                    red = 0.moveColorTo(invertCenter, radialProgress),
                    green = (255 * (1 - rangeProgress)).moveColorTo(invertCenter, radialProgress)
                        .roundToInt(),
                    blue = 255.moveColorTo(invertCenter, radialProgress),
                )
            }

            ColorRange.BlueToMagenta -> {
                Color(
                    red = (255 * rangeProgress).moveColorTo(invertCenter, radialProgress).roundToInt(),
                    green = 0.moveColorTo(invertCenter, radialProgress),
                    blue = 255.moveColorTo(invertCenter, radialProgress),
                )
            }

            ColorRange.MagentaToRed -> {
                Color(
                    red = 255.moveColorTo(invertCenter, radialProgress),
                    green = 0.moveColorTo(invertCenter, radialProgress),
                    blue = (255 * (1 - rangeProgress)).moveColorTo(invertCenter, radialProgress).roundToInt(),
                )
            }
        }
        pointerOffset = getBoundedPointWithInRadius(
            horizontal,
            vertical,
            distanceToCenter,
            radius,
            BoundedPointStrategy.Inside
        )
    }

    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Canvas(modifier = modifier
            .size(pickerSize)
            .onSizeChanged {
                radius = it.width / 2f
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    onGestureEvent(it.x, it.y)
                }
            }
            .pointerInput(Unit){
                detectDragGestures { change, _ ->
                    onGestureEvent(change.position.x, change.position.y)
                }
            }
        ) {
            drawCircle(Brush.sweepGradient(gradientColors))

            drawCircle(
                ShaderBrush(
                    RadialGradientShader(
                        Offset(size.width / 2f, size.height / 2f),
                        colors = listOf(colorPickerCenterColor(invertCenter), Color.Transparent),
                        radius = size.width / 2f
                    )
                )
            )
            drawColorSelector(currentColor, pointerOffset)
        }

        if (showBrightness) {
            Spacer(modifier = Modifier.height(16.dp))
            ColorSlider(
                colors = listOf(colorPickerCenterColor(invertCenter), currentColor),
                selectedColor = currentColor
            ) {
                brightness = 1 - it
            }
        }

        AlphaColorBar(showAlpha, currentColor, initialColor, currentAlpha)
    }

}