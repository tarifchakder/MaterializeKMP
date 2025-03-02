package com.tarif.dynamictheme.colorpicker.util

import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt


internal object ColorPickerHelper {

    private fun calculateLightness(initialColor: Color, rangeColor: Color): Float {
        val initialLuminance = (initialColor.red + initialColor.green + initialColor.blue) / 3
        val rangeLuminance = (rangeColor.red + rangeColor.green + rangeColor.blue) / 3

        return initialLuminance / rangeLuminance
    }

    private fun calculateDarkness(initialColor: Color): Float {
        return 1f - initialColor.alpha
    }

    fun Color.lightness(): Float {
        return (red + green + blue) / 3f
    }

    fun Color.darkness(): Float {
        return 1f - lightness()
    }

    private fun colorDistance(color1: Color, color2: Color): Float {
        return sqrt(
            (color1.red - color2.red).pow(2) +
                    (color1.green - color2.green).pow(2) +
                    (color1.blue - color2.blue).pow(2)
        )
    }
}