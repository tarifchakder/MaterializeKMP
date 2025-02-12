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
import com.tarif.dynamictheme.colorpicker.helper.BoundedPointStrategy
import com.tarif.dynamictheme.colorpicker.helper.MathHelper
import com.tarif.dynamictheme.colorpicker.helper.MathHelper.getBoundedPointWithInRadius
import com.tarif.dynamictheme.colorpicker.helper.MathHelper.getLength
import com.tarif.dynamictheme.colorpicker.model.ColorRange
import com.tarif.dynamictheme.colorpicker.model.Colors.gradientColors
import com.tarif.dynamictheme.extension.blue
import com.tarif.dynamictheme.extension.calculateRangeProgress
import com.tarif.dynamictheme.extension.colorToHSV
import com.tarif.dynamictheme.extension.darken
import com.tarif.dynamictheme.extension.drawColorSelector
import com.tarif.dynamictheme.extension.green
import com.tarif.dynamictheme.extension.lighten
import com.tarif.dynamictheme.extension.red
import io.github.mohammedalaamorsi.colorpicker.helper.ColorPickerHelper.darkness
import io.github.mohammedalaamorsi.colorpicker.helper.ColorPickerHelper.lightness
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
internal fun RingColorPicker(
    modifier: Modifier = Modifier,
    colorPickerSize: Dp,
    ringWidth: Dp,
    previewRadius: Dp,
    showLightColorBar: Boolean,
    showDarkColorBar: Boolean,
    showAlphaBar: Boolean,
    showColorPreview: Boolean,
    initialColor: Color = Color.Red,
    onPickedColor: (Color) -> Unit,
) {
    val density = LocalDensity.current
    val ringWidthPx = remember { with(density) { ringWidth.toPx() } }
    val previewRadiusPx = remember { with(density) { previewRadius.toPx() } }
    var radius by remember { mutableStateOf(0f) }
    var pickerLocation by remember(radius) {
        mutableStateOf(
            getBoundedPointWithInRadius(
                x = radius * 2,
                y = radius,
                length = getLength(radius * 2, radius, radius),
                radius = radius - ringWidthPx / 2,
                strategy = BoundedPointStrategy.Edge
            )
        )
    }
    var selectedColor by remember { mutableStateOf(initialColor) }
    var color by remember { mutableStateOf(initialColor) }
    var lightColor by remember { mutableStateOf(initialColor) }
    var darkColor by remember { mutableStateOf(initialColor) }
    val lightness = remember { mutableStateOf(initialColor.lightness()) }
    val darkness = remember { mutableStateOf(initialColor.darkness()) }
    val alpha = remember { mutableStateOf(initialColor.alpha) }

    LaunchedEffect(initialColor, radius) {
        if (radius > 0) {
            val hsv=colorToHSV(initialColor)
            val angle = MathHelper.toRadians(hsv[0].toDouble())
            val saturation = hsv[1]

            val x = radius + cos(angle) * saturation * radius
            val y = radius + sin(angle) * saturation * radius
            pickerLocation = Offset(x.toFloat(), y.toFloat())
        }
    }

    LaunchedEffect(selectedColor, lightness, darkness, alpha) {
        var red = selectedColor.red().lighten(lightness.value)
        var green = selectedColor.green().lighten(lightness.value)
        var blue = selectedColor.blue().lighten(lightness.value)
        lightColor = Color(red, green, blue, 255)
        red = red.darken(darkness.value)
        green = green.darken(darkness.value)
        blue = blue.darken(darkness.value)
        darkColor = Color(red, green, blue, 255)
        color = Color(red, green, blue, (255 * alpha.value).roundToInt())
        onPickedColor(color)
    }

    fun onGestureEvent(x: Float, y: Float) {
        val angle = ((atan2(y - radius, x - radius) * 180.0 / PI) + 360) % 360
        val length = getLength(x, y, radius)
        val progress = angle / 360f
        val (rangeProgress, range) = calculateRangeProgress(progress)

        val (red, green, blue) = when (range) {
            ColorRange.RedToYellow -> Triple(255, (255 * rangeProgress).roundToInt(), 0)

            ColorRange.YellowToGreen -> Triple((255 * (1 - rangeProgress)).roundToInt(), 255, 0)

            ColorRange.GreenToCyan -> Triple(0, 255, (255 * rangeProgress).roundToInt())

            ColorRange.CyanToBlue -> Triple(0, (255 * (1 - rangeProgress)).roundToInt(), 255)

            ColorRange.BlueToPurple -> Triple((255 * rangeProgress).roundToInt(), 0, 255)

            ColorRange.PurpleToRed -> Triple(255, 0, (255 * (1 - rangeProgress)).roundToInt())
        }

        pickerLocation = getBoundedPointWithInRadius(x, y, length, radius - ringWidthPx / 2, BoundedPointStrategy.Edge)
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
                radius = radius - ringWidthPx / 2f,
                style = Stroke(ringWidthPx)
            )
            if (showColorPreview) {
                drawCircle(color, radius = previewRadiusPx)
            }
            drawColorSelector(selectedColor, pickerLocation)
        }

        LightColorBar(showLightColorBar, selectedColor, initialColor, lightness)

        DarkColorBar(showDarkColorBar, lightColor, initialColor, darkness)

        AlphaColorBar(showAlphaBar, darkColor, initialColor, alpha)

    }
}

