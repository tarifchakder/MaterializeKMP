package com.tarifchakder.materializekmp

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.tarifchakder.materializekmp.extension.invertColors
import com.tarifchakder.materializekmp.extension.toAmoled
import com.tarifchakder.materializekmp.extension.toColorScheme
import com.tarifchakder.materializekmp.extension.toDynamicScheme


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
fun getColorSchemes(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    isAmoled: Boolean,
    colorTuple: ColorTuple,
    paletteStyle: PaletteStyle,
    contrastLevel: Double,
    isInvertColors: Boolean
): ColorScheme {
    val dynamicScheme = colorTuple.toDynamicScheme(isDynamicColor, isDarkTheme, paletteStyle, contrastLevel)

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



