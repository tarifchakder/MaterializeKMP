package com.tarif.dynamictheme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.tarif.dynamictheme.colortuple.ColorTuple
import com.tarif.dynamictheme.ktx.invertColors
import com.tarif.dynamictheme.ktx.toAmoled
import com.tarif.dynamictheme.ktx.toColorScheme
import com.tarif.dynamictheme.ktx.toDynamicScheme

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
fun rememberDynamicColorScheme(
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
                outlineVariant = onSecondaryContainer.copy(alpha = 0.2f)
                    .compositeOver(surfaceColorAtElevation(6.dp))
            )
        }
}



