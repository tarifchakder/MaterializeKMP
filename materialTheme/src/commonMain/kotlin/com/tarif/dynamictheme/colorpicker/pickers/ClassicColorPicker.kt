package com.tarif.dynamictheme.colorpicker.pickers

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
import com.tarif.dynamictheme.colorpicker.component.AlphaColorBar
import com.tarif.dynamictheme.colorpicker.model.ColorRange
import com.tarif.dynamictheme.colorpicker.model.Colors.gradientColors
import com.tarif.dynamictheme.extension.blue
import com.tarif.dynamictheme.extension.calculateRangeProgress
import com.tarif.dynamictheme.extension.darken
import com.tarif.dynamictheme.extension.drawColorSelector
import com.tarif.dynamictheme.extension.green
import com.tarif.dynamictheme.extension.lighten
import com.tarif.dynamictheme.extension.red
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@Composable
internal fun ClassicColorPicker(
    modifier: Modifier = Modifier,
    showAlphaBar: Boolean,
    initialColor: Color = Color.Red,
    size: Dp = 200.dp,
    onPickedColor: (Color) -> Unit
) {
    var pickerLocation by remember { mutableStateOf(Offset.Zero) }
    var colorPickerSize by remember { mutableStateOf(IntSize(1, 1)) }
    val alpha = remember { mutableStateOf(initialColor.alpha) }
    var rangeColor by remember { mutableStateOf(initialColor) }
    var color by remember { mutableStateOf(initialColor) }

    LaunchedEffect(rangeColor, pickerLocation, colorPickerSize, alpha) {
        val xProgress = (1f - (pickerLocation.x / colorPickerSize.width)).coerceIn(0f, 1f)
        val yProgress = (pickerLocation.y / colorPickerSize.height).coerceIn(0f, 1f)
        if (xProgress.isNaN().not() && yProgress.isNaN().not()) {
            color = Color(
                rangeColor
                    .red()
                    .lighten(xProgress)
                    .darken(yProgress),
                rangeColor
                    .green()
                    .lighten(xProgress)
                    .darken(yProgress),
                rangeColor
                    .blue()
                    .lighten(xProgress)
                    .darken(yProgress),
                alpha = (255 * alpha.value).roundToInt()
            )
        }
    }

    LaunchedEffect(color) {
        onPickedColor(color)
    }

    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Box(
            modifier = modifier
                .onSizeChanged {
                    colorPickerSize = it
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        val x = it.x.coerceIn(0f, colorPickerSize.width.toFloat())
                        val y = it.y.coerceIn(0f, colorPickerSize.height.toFloat())
                        pickerLocation = Offset(x, y)
                    }
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, _, _, _ ->
                        val x = centroid.x.coerceIn(0f, colorPickerSize.width.toFloat())
                        val y = centroid.y.coerceIn(0f, colorPickerSize.height.toFloat())
                        pickerLocation = Offset(x, y)
                    }
                }.size(size)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                drawRect(Brush.horizontalGradient(listOf(Color.White, rangeColor)))
                drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                this.drawColorSelector(color, pickerLocation)
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        ColorSlider(
            colors = gradientColors,
            initialColor = initialColor
        ) {
            val (rangeProgress, range) = calculateRangeProgress(it.toDouble())
            val red: Int
            val green: Int
            val blue: Int
            when (range) {
                ColorRange.RedToYellow -> {
                    red = 255
                    green = (255 * rangeProgress).roundToInt()
                    blue = 0
                }

                ColorRange.YellowToGreen -> {
                    red = (255 * (1 - rangeProgress)).roundToInt()
                    green = 255
                    blue = 0
                }

                ColorRange.GreenToCyan -> {
                    red = 0
                    green = 255
                    blue = (255 * rangeProgress).roundToInt()
                }

                ColorRange.CyanToBlue -> {
                    red = 0
                    green = (255 * (1 - rangeProgress)).roundToInt()
                    blue = 255
                }

                ColorRange.BlueToPurple -> {
                    red = (255 * rangeProgress).roundToInt()
                    green = 0
                    blue = 255
                }

                ColorRange.PurpleToRed -> {
                    red = 255
                    green = 0
                    blue = (255 * (1 - rangeProgress)).roundToInt()
                }
            }
            rangeColor = Color(red, green, blue)
        }

        AlphaColorBar(showAlphaBar, rangeColor, initialColor, alpha)

    }

}
