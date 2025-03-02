package com.tarif.dynamictheme.colorpicker.pickers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.tarif.dynamictheme.colorpicker.component.AlphaColorBar
import com.tarif.dynamictheme.colorpicker.component.DarkColorBar
import com.tarif.dynamictheme.colorpicker.component.LightColorBar
import com.tarif.dynamictheme.colorpicker.model.ColorRange
import com.tarif.dynamictheme.colorpicker.model.Colors.gradientColors
import com.tarif.dynamictheme.colorpicker.util.BoundedPointStrategy
import com.tarif.dynamictheme.colorpicker.util.ColorPickerHelper.darkness
import com.tarif.dynamictheme.colorpicker.util.ColorPickerHelper.lightness
import com.tarif.dynamictheme.colorpicker.util.MathHelper
import com.tarif.dynamictheme.colorpicker.util.MathHelper.getBoundedPointWithInRadius
import com.tarif.dynamictheme.colorpicker.util.MathHelper.getLength
import com.tarif.dynamictheme.extension.blue
import com.tarif.dynamictheme.extension.calculateRangeProgress
import com.tarif.dynamictheme.extension.colorToHSV
import com.tarif.dynamictheme.extension.darken
import com.tarif.dynamictheme.extension.drawColorSelector
import com.tarif.dynamictheme.extension.green
import com.tarif.dynamictheme.extension.lighten
import com.tarif.dynamictheme.extension.red
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * A composable function that displays a ring-based color picker.
 *
 * This color picker allows the user to select a color by interacting with a circular ring.
 * It also provides optional controls for lightness, darkness, and opacity, as well as a preview indicator.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param colorPickerSize The size of the color picker */
@Composable
internal fun RingColorPicker(
    modifier: Modifier = Modifier,
    colorPickerSize: Dp,
    ringThickness: Dp,
    previewIndicatorRadius: Dp,
    showLightnessControl: Boolean,
    showDarkColorBar: Boolean,
    showOpacityControl: Boolean,
    showPreviewIndicator: Boolean,
    seedColor: Color = Color.Red,
    onColorChanged: (Color) -> Unit,
) {
    val density = LocalDensity.current
    val ringThicknessPx = remember { with(density) { ringThickness.toPx() } }
    val previewIndicatorRadiusPx = remember { with(density) { previewIndicatorRadius.toPx() } }
    var radius by remember { mutableStateOf(0f) }
    var colorPickerPosition by remember(radius) {
        mutableStateOf(
            getBoundedPointWithInRadius(
                x = radius * 2,
                y = radius,
                length = getLength(radius * 2, radius, radius),
                radius = radius - ringThicknessPx / 2,
                strategy = BoundedPointStrategy.Edge
            )
        )
    }
    var selectedColor by remember { mutableStateOf(seedColor) }
    var color by remember { mutableStateOf(seedColor) }
    var selectedLightColor by remember { mutableStateOf(seedColor) }
    var darkColor by remember { mutableStateOf(seedColor) }
    val lightnessFactor = remember { mutableStateOf(seedColor.lightness()) }
    val darkness = remember { mutableStateOf(seedColor.darkness()) }
    val alpha = remember { mutableStateOf(seedColor.alpha) }

    LaunchedEffect(seedColor, radius) {
        if (radius > 0) {
            val hsv=colorToHSV(seedColor)
            val angle = MathHelper.toDegrees(hsv[0].toDouble())
            val saturation = hsv[1]

            val touchX = radius + cos(angle) * saturation * radius
            val touchY = radius + sin(angle) * saturation * radius
            colorPickerPosition = Offset(touchX.toFloat(), touchY.toFloat())
        }
    }

    LaunchedEffect(selectedColor, lightnessFactor, darkness, alpha) {
        var red = selectedColor.red().lighten(lightnessFactor.value)
        var green = selectedColor.green().lighten(lightnessFactor.value)
        var blue = selectedColor.blue().lighten(lightnessFactor.value)
        selectedLightColor = Color(red, green, blue, 255)
        red = red.darken(darkness.value)
        green = green.darken(darkness.value)
        blue = blue.darken(darkness.value)
        darkColor = Color(red, green, blue, 255)
        color = Color(red, green, blue, (255 * alpha.value).roundToInt())
        onColorChanged(color)
    }

    fun onGestureEvent(touchX: Float, touchY: Float) {
        val angle = ((atan2(touchY - radius, touchX - radius) * 180.0 / PI) + 360) % 360
        val length = getLength(touchX, touchY, radius)
        val progress = angle / 360f
        val (rangeProgress, range) = calculateRangeProgress(progress)

        val (red, green, blue) = when (range) {
            ColorRange.RedToYellow -> Triple(255, (255 * rangeProgress).roundToInt(), 0)

            ColorRange.YellowToGreen -> Triple((255 * (1 - rangeProgress)).roundToInt(), 255, 0)

            ColorRange.GreenToCyan -> Triple(0, 255, (255 * rangeProgress).roundToInt())

            ColorRange.CyanToBlue -> Triple(0, (255 * (1 - rangeProgress)).roundToInt(), 255)

            ColorRange.BlueToMagenta -> Triple((255 * rangeProgress).roundToInt(), 0, 255)

            ColorRange.MagentaToRed -> Triple(255, 0, (255 * (1 - rangeProgress)).roundToInt())
        }

        colorPickerPosition = getBoundedPointWithInRadius(touchX, touchY, length, radius - ringThicknessPx / 2, BoundedPointStrategy.Edge)
        selectedColor = Color(red, green, blue)
    }

    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Canvas(modifier = modifier
            .size(colorPickerSize)
            .onSizeChanged {
                radius = it.width.toFloat() / 2
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    onGestureEvent(it.x, it.y)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onGestureEvent(change.position.x, change.position.y)
                }
            }
        ) {
            drawCircle(
                Brush.sweepGradient(gradientColors),
                radius = radius - ringThicknessPx / 2f,
                style = Stroke(ringThicknessPx)
            )
            if (showPreviewIndicator) {
                drawCircle(color, radius = previewIndicatorRadiusPx)
            }
            drawColorSelector(selectedColor, colorPickerPosition)
        }

        LightColorBar(showLightnessControl, selectedColor, seedColor, lightnessFactor)

        DarkColorBar(showDarkColorBar, selectedLightColor, seedColor, darkness)

        AlphaColorBar(showOpacityControl, darkColor, seedColor, alpha)

    }
}

