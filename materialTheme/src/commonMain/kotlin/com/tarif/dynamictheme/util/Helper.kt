package com.tarif.dynamictheme.util

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

internal object Helper {

    /**
     * Blend between two ARGB colors using the given ratio.
     *
     * <p>A blend ratio of 0.0 will result in {@code color1}, 0.5 will give an even blend,
     * 1.0 will result in {@code color2}.</p>
     *
     * @param color1 the first ARGB color
     * @param color2 the second ARGB color
     * @param ratio the blend ratio of {@code color1} to {@code color2}
     *
     * Ensure the fraction is between 0 and 1
     */
    @ColorInt
    fun blendARGB(
        @ColorInt color1: Int, @ColorInt color2: Int,
        @FloatRange(from = 0.0, to = 1.0) ratio: Float
    ): Int {
        val normalizedFraction = ratio.coerceIn(0f, 1f)

        // Extract RGBA components from color1
        val r1 = Color(color1).red
        val g1 = Color(color1).green
        val b1 = Color(color1).blue
        val a1 = Color(color1).alpha

        // Extract RGBA components from color2
        val r2 = Color(color2).red
        val g2 = Color(color2).green
        val b2 = Color(color2).blue
        val a2 = Color(color2).alpha

        // Blend the alpha component
        val blendedAlpha = (a1 * (1 - normalizedFraction) + a2 * normalizedFraction).toInt()

        // Blend the RGB components
        val blendedRed = (r1 * (1 - normalizedFraction) + r2 * normalizedFraction).toInt()
        val blendedGreen = (g1 * (1 - normalizedFraction) + g2 * normalizedFraction).toInt()
        val blendedBlue = (b1 * (1 - normalizedFraction) + b2 * normalizedFraction).toInt()

        // Return the composed color
        return Color(blendedRed, blendedGreen, blendedBlue, blendedAlpha).toArgb()
    }

}