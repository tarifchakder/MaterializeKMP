package com.tarif.dynamictheme.colorpicker.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class ColorPickerType {
    /**
     * @param showAlphaBar Sets the visibility of the alpha bar.
     */
    class Classic(
        val showAlphaBar: Boolean = true,
        val initialColor: Color = Color.Red,
        val colorPickerSize: Dp = 200.dp
    ) : ColorPickerType()

    /**
     * @param showBrightnessBar Sets the visibility of the brightness bar.
     * @param showAlphaBar Sets the visibility of the alpha bar.
     * @param lightCenter Changes the center of the circle to black or white.
     */
    class Circle(
        val showBrightnessBar: Boolean = true,
        val showAlphaBar: Boolean = true,
        val lightCenter: Boolean = true,
        val initialColor: Color = Color.Red,
        val colorPickerSize: Dp = 200.dp
    ) : ColorPickerType()

    /**
     * @param ringWidth Sets the color ring width.
     * @param previewRadius Sets the radius of the center color preview circle.
     * @param showLightnessBar Sets the visibility of the lightness bar.
     * @param showDarknessBar Sets the visibility of the darkness bar.
     * @param showAlphaBar Sets the visibility of the alpha bar.
     * @param showColorPreview Sets the visibility of the center color preview circle.
     */
    class Ring(
        val ringWidth: Dp = 10.dp,
        val previewRadius: Dp = 80.dp,
        val showLightnessBar: Boolean = true,
        val showDarknessBar: Boolean = true,
        val showAlphaBar: Boolean = true,
        val showColorPreview: Boolean = true,
        val initialColor: Color = Color.Green,
        size: Dp = 200.dp
    ) : ColorPickerType()

    /**
     * @param colorWidth Arc width of all colors.
     * @param tracksCount Amount of the tracks.
     * @param sectorsCount Amount of the sectors for each track.
     */
    class SimpleRing(
        val colorWidth: Dp = 20.dp,
        val tracksCount: Int = 5,
        val sectorsCount: Int = 24,
        val initialColor: Color = Color.Green,
        size: Dp = 200.dp
    ) : ColorPickerType()
}