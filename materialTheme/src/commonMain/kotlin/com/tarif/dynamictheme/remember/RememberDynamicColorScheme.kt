package com.tarif.dynamictheme.remember

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tarif.dynamictheme.ColorTuple
import com.tarif.dynamictheme.PaletteStyle
import com.tarif.dynamictheme.getColorScheme

/**
 * Create and remember a custom [ColorScheme] based on the provided [ColorTuple].
 *
 * If a colorTuple is not provided, then the color palette will be generated from the [style] and [colorTuple].
 *
 * @param[isDarkTheme] Whether the scheme should be dark or light.
 * @param[isDynamicColor] Whether the scheme should be dynamic.
 * @param[isAmoled] Whether the dark scheme is used with Amoled screen (Pure dark).
 * @param[isInvertColors] Scheme color inverted if enabled.
 * @param[style] The style of the scheme.
 * @param[colorTuple] ColorScheme generate based on seed color else it will use PaletteStyle color .
 * @param[contrastLevel] The contrast level of the scheme.
 */
@Composable
fun rememberColorScheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    isAmoled: Boolean,
    isInvertColors: Boolean,
    colorTuple: ColorTuple,
    style: PaletteStyle,
    contrastLevel: Double,
): ColorScheme {
    return remember(
        isDarkTheme,
        isDynamicColor,
        isAmoled,
        isInvertColors,
        colorTuple,
        style,
        contrastLevel
    ) {
        getColorScheme(
            isDarkTheme = isDarkTheme,
            isDynamicColor = isDynamicColor,
            isAmoled = isAmoled,
            colorTuple = colorTuple,
            style = style,
            contrastLevel = contrastLevel,
            isInvertColors = isInvertColors
        )
    }
}