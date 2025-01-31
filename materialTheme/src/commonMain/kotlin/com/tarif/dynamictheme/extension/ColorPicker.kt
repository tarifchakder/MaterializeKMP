package com.tarif.dynamictheme.extension

import androidx.compose.ui.graphics.Color
import com.tarif.dynamictheme.colorpicker.data.ColorRange
import kotlin.math.pow
import kotlin.math.sqrt

internal fun calculateRangeProgress(progress: Double): Pair<Double, ColorRange> {
    val rangeIndex = (progress * 6).toInt()
    val range = when (rangeIndex) {
        0 -> ColorRange.RedToYellow
        1 -> ColorRange.YellowToGreen
        2 -> ColorRange.GreenToCyan
        3 -> ColorRange.CyanToBlue
        4 -> ColorRange.BlueToPurple
        else -> ColorRange.PurpleToRed
    }
    return progress * 6 - rangeIndex to range
}

internal fun calculateInitialProgress(initialColor: Color, colors: List<Color>): Float {
    if (colors.isEmpty() || colors.size == 1) return 0f

    val bestMatchIndex = colors.indices.minByOrNull { colorDistance(initialColor, colors[it]) } ?: 0

    val startColor = colors[bestMatchIndex]
    val endColor = colors.getOrNull(bestMatchIndex + 1) ?: colors.last()
    val rangeDistance = colorDistance(startColor, endColor)
    val progressWithinRange = (colorDistance(startColor, initialColor) / rangeDistance).coerceIn(0f, 1f)

    return (bestMatchIndex + progressWithinRange) / (colors.size - 1)
}

private fun colorDistance(color1: Color, color2: Color): Float {
    return sqrt(squaredDiff(color1.red, color2.red) + squaredDiff(color1.green, color2.green) + squaredDiff(color1.blue, color2.blue))
}

private fun squaredDiff(a: Float, b: Float) = (a - b).pow(2)
