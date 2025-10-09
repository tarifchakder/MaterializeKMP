package com.tarifchakder.materializekmp.colorpicker.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Sealed class representing different types of color pickers.
 *
 * This class defines the common properties and configuration options for various color picker
 * types. It provides a structured way to manage different styles and features of a color picker,
 * such as classic, circle, and ring designs. Each subclass represents a unique color picker type
 * with its own specific set of parameters.
 *
 * The common properties are:
 * - [startColor]: The initial color that the picker will display.
 * - [colorPickerSize]: The size of the main color picker area.
 */
sealed class ColorPickerType {

    abstract val startColor: Color
    abstract val colorPickerSize: Dp

    /**
     * Represents the classic color picker type.
     *
     * @param colorPickerSize Sets the size of the color preview.
     * @param isAlphaBarVisible Sets the visibility of the alpha bar.
     * @param startColor The initial color of the picker.
     */
    data class Classic(
        override val colorPickerSize: Dp = 200.dp,
        val isAlphaBarVisible: Boolean = true,
        override val startColor: Color = Color.Red
    ) : ColorPickerType()

    /**
     * Represents the circle color picker type.
     *
     * @param colorPickerSize Sets the size of the color preview.
     * @param isBrightnessBarVisible Sets the visibility of the brightness bar.
     * @param isAlphaBarVisible Sets the visibility of the alpha bar.
     * @param isLightCenter Changes the center of the circle to black or white.
     * @param startColor The initial color of the picker.
     */
    data class Circle(
        override val colorPickerSize: Dp = 200.dp,
        val isBrightnessBarVisible: Boolean = true,
        val isAlphaBarVisible: Boolean = true,
        val isLightCenter: Boolean = true,
        override val startColor: Color = Color.Red
    ) : ColorPickerType()

    /**
     * Represents the ring color picker type.
     *
     * @param colorPickerSize Sets the size of the color preview circle.
     * @param colorRingWidth Sets the color ring width.
     * @param previewCircleRadius Sets the radius of the center color preview circle.
     * @param isLightnessBarVisible Sets the visibility of the lightness bar.
     * @param isDarknessBarVisible Sets the visibility of the darkness bar.
     * @param isAlphaBarVisible Sets the visibility of the alpha bar.
     * @param isColorPreviewVisible Sets the visibility of the center color preview circle.
     * @param startColor The initial color of the picker.
     */
    data class Ring(
        override val colorPickerSize: Dp = 200.dp,
        val colorRingWidth: Dp = 10.dp,
        val previewCircleRadius: Dp = 80.dp,
        val isLightnessBarVisible: Boolean = true,
        val isDarknessBarVisible: Boolean = true,
        val isAlphaBarVisible: Boolean = true,
        val isColorPreviewVisible: Boolean = true,
        override val startColor: Color = Color.Red
    ) : ColorPickerType()
}