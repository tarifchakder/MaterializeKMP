package com.tarif.materializekmp.remember

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tarif.materializekmp.ColorTuple
import com.tarif.materializekmp.PaletteStyle
import com.tarif.materializekmp.getColorSchemes

/**
- * Create and remember a custom [ColorScheme] based on the provided [ColorTuple].
+ * Creates and remembers a custom [ColorScheme] based on the provided parameters.
 *
- * If a colorTuple is not provided, then the color palette will be generated from the [paletteStyle] and [colorTuple].
+ * This function generates a [ColorScheme] either dynamically or statically, and caches the result.
+ * The resulting [ColorScheme] can be either dark or light, and can be customized with various options.
 *
- * @param[isDarkTheme] Whether the scheme should be dark or light.
- * @param[isDynamicColor] Whether the scheme should be dynamic.
- * @param[isAmoled] Whether the dark scheme is used with Amoled screen (Pure dark).
- * @param[isInvertColors] Scheme color inverted if enabled.
- * @param[paletteStyle] The paletteStyle of the scheme.
- * @param[colorTuple] ColorScheme generate based on seed color else it will use PaletteStyle color .
- * @param[contrastLevel] The contrast level of the scheme.
+ * **Parameters:**
+ *
+ * @param isDarkTheme `true` if a dark color scheme should be used, `false` for a light color scheme.
+ * @param isDynamicColor `true` if the color scheme should be dynamically generated based on system settings, `false` for a static color scheme.
+ * @param isAmoled `true` if the dark color scheme should use pure black (optimized for AMOLED screens), `false` otherwise. Only applicable when `isDarkTheme` is `true`.
+ * @param isInvertColors `true` if the colors of the scheme should be inverted, `false` otherwise.
+ * @param colorTuple A [ColorTuple] representing the seed colors for generating the color scheme. If provided, the color scheme will be generated based on these seed colors. If null, the scheme will be generated based on the [paletteStyle].
+ * @param paletteStyle A [PaletteStyle] defining the overall paletteStyle of the color scheme. Used when a */
@Composable
fun rememberColorScheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    isAmoled: Boolean,
    isInvertColors: Boolean,
    colorTuple: ColorTuple,
    paletteStyle: PaletteStyle,
    contrastLevel: Double,
): ColorScheme {
    return remember(
        isDarkTheme,
        isDynamicColor,
        isAmoled,
        isInvertColors,
        colorTuple,
        paletteStyle,
        contrastLevel
    ) {
        getColorSchemes(
            isDarkTheme = isDarkTheme,
            isDynamicColor = isDynamicColor,
            isAmoled = isAmoled,
            colorTuple = colorTuple,
            paletteStyle = paletteStyle,
            contrastLevel = contrastLevel,
            isInvertColors = isInvertColors
        )
    }
}