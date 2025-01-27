package com.tarif.dynamictheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.tarif.dynamictheme.extension.invertColors
import com.tarif.dynamictheme.extension.toAmoled
import com.tarif.dynamictheme.extension.toColorScheme
import com.tarif.dynamictheme.extension.toDynamicScheme

/**
 * Create a custom [ColorScheme] based on the provided colors.
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
fun getColorScheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    isAmoled: Boolean,
    colorTuple: ColorTuple,
    style: PaletteStyle,
    contrastLevel: Double,
    isInvertColors: Boolean
): ColorScheme {
    val dynamicScheme = colorTuple.toDynamicScheme(isDynamicColor, isDarkTheme, style, contrastLevel)

    val colorScheme = if (isDarkTheme) {
        dynamicScheme.toColorScheme().toAmoled(isAmoled)
    } else {
        dynamicScheme.toColorScheme()
    }

    return colorScheme
        .invertColors(isInvertColors && !isDynamicColor)
        .run {
            copy(
                outlineVariant = onSecondaryContainer
                    .copy(alpha = 0.2f)
                    .compositeOver(surfaceColorAtElevation(6.dp))
            )
        }
}



