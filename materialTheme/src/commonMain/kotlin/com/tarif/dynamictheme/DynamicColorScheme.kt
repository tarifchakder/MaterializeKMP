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
- * Create a custom [ColorScheme] based on the provided colors.
+ * Creates a custom [ColorScheme] based on the provided parameters.
 *
- * If a colorTuple is not provided, then the color palette will be generated from the [paletteStyle] and [colorTuple].
+ * This function allows you to generate a Material Design [ColorScheme] that can be customized
+ * for various scenarios, including light/dark themes, dynamic coloring, AMOLED displays, and
+ * color inversion.
 *
- * @param[isDarkTheme] Whether the scheme should be dark or light.
- * @param[isDynamicColor] Whether the scheme should be dynamic.
- * @param[isAmoled] Whether the dark scheme is used with Amoled screen (Pure dark).
- * @param[isInvertColors] Scheme color inverted if enabled.
- * @param[paletteStyle] The paletteStyle of the scheme.
- * @param[colorTuple] ColorScheme generate based on seed color else it will use PaletteStyle color .
- * @param[contrastLevel] The contrast level of the scheme.
+ * @param isDarkTheme `true` if the color scheme should be dark, `false` for light.
+ * @param isDynamicColor `true` if the color scheme should adapt dynamically to the user's wallpaper
+ *                       (if supported), `false` for a static color scheme.
+ * @param isAmoled `true` if the dark scheme is intended for AMOLED displays (pure black background),
+ *                 `false` for a standard dark theme. Only applicable when `isDarkTheme` is `true`.
+ * @param colorTuple A [ColorTuple] that represents the base color seed to construct the color scheme.
+ *                   If `isDynamicColor` is false, the color scheme is constructed using this seed color,
+ *                   otherwise it's used as a fallback or starting point.
+ * @param paletteStyle The [PaletteStyle] to apply to the color scheme. If no seed is provided it will generate the color scheme using palette color.
+ * @param contrastLevel A `Double` value representing the desired contrast level for the color scheme.
+ */
fun getColorScheme(
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



