package com.tarif.dynamictheme.colorpicker.pickers


import androidx.compose.foundation.Canvas
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
import com.tarif.dynamictheme.colorpicker.component.AlphaColorBar
import com.tarif.dynamictheme.colorpicker.helper.BoundedPointStrategy
import com.tarif.dynamictheme.colorpicker.helper.MathHelper
import com.tarif.dynamictheme.colorpicker.helper.MathHelper.getBoundedPointWithInRadius
import com.tarif.dynamictheme.colorpicker.helper.MathHelper.getLength
import com.tarif.dynamictheme.colorpicker.model.ColorRange
import com.tarif.dynamictheme.colorpicker.model.Colors.gradientColors
import com.tarif.dynamictheme.extension.blue
import com.tarif.dynamictheme.extension.calculateRangeProgress
import com.tarif.dynamictheme.extension.colorPickerCenterColor
import com.tarif.dynamictheme.extension.colorToHSV
import com.tarif.dynamictheme.extension.drawColorSelector
import com.tarif.dynamictheme.extension.green
import com.tarif.dynamictheme.extension.moveColorTo
import com.tarif.dynamictheme.extension.red
import com.tarif.dynamictheme.extension.toDegrees
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
internal fun CircleColorPicker(
    modifier: Modifier = Modifier,
    colorPickerSize: Dp = 200.dp,
    showAlphaBar: Boolean,
    showBrightnessBar: Boolean,
    lightCenter: Boolean,
    initialColor: Color = Color.Red,
    onPickedColor: (Color) -> Unit
) {
    var radius by remember { mutableStateOf(0f) }
    var pickerLocation by remember { mutableStateOf(Offset(radius, radius)) }
    var pickerColor by remember { mutableStateOf(initialColor) }
    var brightness by remember { mutableStateOf(1f) }
    val alpha = remember { mutableStateOf(initialColor.alpha) }

    LaunchedEffect(initialColor) {
            val hsv = colorToHSV(initialColor)
            val angle = MathHelper.toRadians(hsv[0].toDouble())
            val saturation = hsv[1]
            brightness = hsv[2]

            val x = radius + cos(angle) * saturation
            val y = radius + sin(angle) * saturation
            pickerLocation = Offset(x.toFloat(), y.toFloat())
            pickerColor = initialColor
    }

    LaunchedEffect(brightness, pickerColor, alpha) {
            onPickedColor(
                Color(
                    pickerColor.red().moveColorTo(!lightCenter, brightness),
                    pickerColor.green().moveColorTo(!lightCenter, brightness),
                    pickerColor.blue().moveColorTo(!lightCenter, brightness),
                    (255 * alpha.value).roundToInt()
                )
            )
    }

    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
        Canvas(modifier = modifier
            .size(colorPickerSize)
            .onSizeChanged {
                radius = it.width / 2f
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    val x = it.x
                    val y = it.y
                    val angle = (toDegrees(atan2(y - radius, x - radius).toDouble()) + 360) % 360
                    val length = getLength(x, y, radius)
                    val radiusProgress = 1 - (length / radius).coerceIn(0f, 1f)
                    val angleProgress = angle / 360f
                    val (rangeProgress, range) = calculateRangeProgress(angleProgress)

                    pickerColor = when (range) {
                        ColorRange.RedToYellow -> {
                            Color(
                                red = 255.moveColorTo(lightCenter, radiusProgress),
                                green = (255f * rangeProgress).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                                blue = 0.moveColorTo(lightCenter, radiusProgress),
                            )
                        }

                        ColorRange.YellowToGreen -> {
                            Color(
                                red = (255 * (1 - rangeProgress)).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                                green = 255.moveColorTo(lightCenter, radiusProgress),
                                blue = 0.moveColorTo(lightCenter, radiusProgress),
                            )
                        }

                        ColorRange.GreenToCyan -> {
                            Color(
                                red = 0.moveColorTo(lightCenter, radiusProgress),
                                green = 255.moveColorTo(lightCenter, radiusProgress),
                                blue = (255 * rangeProgress).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                            )
                        }

                        ColorRange.CyanToBlue -> {
                            Color(
                                red = 0.moveColorTo(lightCenter, radiusProgress),
                                green = (255 * (1 - rangeProgress)).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                                blue = 255.moveColorTo(lightCenter, radiusProgress),
                            )
                        }

                        ColorRange.BlueToPurple -> {
                            Color(
                                red = (255 * rangeProgress).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                                green = 0.moveColorTo(lightCenter, radiusProgress),
                                blue = 255.moveColorTo(lightCenter, radiusProgress),
                            )
                        }

                        ColorRange.PurpleToRed -> {
                            Color(
                                red = 255.moveColorTo(lightCenter, radiusProgress),
                                green = 0.moveColorTo(lightCenter, radiusProgress),
                                blue = (255 * (1 - rangeProgress)).moveColorTo(lightCenter, radiusProgress).roundToInt(),
                            )
                        }
                    }
                    pickerLocation = getBoundedPointWithInRadius(
                        x,
                        y,
                        length,
                        radius,
                        BoundedPointStrategy.Inside
                    )
                }
            }
        ) {
            drawCircle(Brush.sweepGradient(gradientColors))

            drawCircle(
                ShaderBrush(
                    RadialGradientShader(
                        Offset(size.width / 2f, size.height / 2f),
                        colors = listOf(
                            colorPickerCenterColor(lightCenter),
                            Color.Transparent
                        ),
                        radius = size.width / 2f
                    )
                )
            )
            drawColorSelector(pickerColor, pickerLocation)
        }

        if (showBrightnessBar) {
            Spacer(modifier = Modifier.height(16.dp))
            ColorSlider(
                colors = listOf(
                    colorPickerCenterColor(lightCenter),
                    pickerColor
                ),
                initialColor = pickerColor
            ) {
                brightness = 1 - it
            }
        }

        AlphaColorBar(showAlphaBar, pickerColor, initialColor, alpha)
    }

}