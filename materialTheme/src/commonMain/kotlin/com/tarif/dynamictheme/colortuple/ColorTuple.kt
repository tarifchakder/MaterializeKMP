package com.tarif.dynamictheme.colortuple

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/** Class that represents App color scheme based on three main colors
 *  @param primary primary color
 *  @param secondary secondary color
 *  @param tertiary tertiary color
 */
data class ColorTuple(
    val primary: Color,
    val secondary: Color? = null,
    val tertiary: Color? = null,
    val surface: Color? = null
) {
    override fun toString(): String {
        return "ColorTuple(primary=${primary.toArgb()}, secondary=${secondary?.toArgb()}, tertiary=${tertiary?.toArgb()}, surface=${surface?.toArgb()})"
    }
}