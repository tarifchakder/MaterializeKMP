package com.tarif.dynamictheme.colortuple

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tarif.dynamictheme.getColorScheme
import com.tarif.dynamictheme.getObserveAsState

/** Class that represents App color scheme based on three main colors
 *  @param primary primary color
 *  @param secondary secondary color
 *  @param tertiary tertiary color
 */
@Stable
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

/**
 * Creates and remember [ColorTuple] instance to based on the three main colors
 * */
@Composable
fun rememberColorTuple(
    colorTuple: ColorTuple,
    isDynamicColor: Boolean,
    isDarkTheme: Boolean
): ColorTuple {
    var onTrigger by remember { mutableStateOf(false) }

    var colorScheme : ColorScheme? = getColorScheme(isDarkTheme)

    if (onTrigger) colorScheme =  getColorScheme(isDarkTheme)

    return remember(
        getObserveAsState(),
        colorTuple,
        isDynamicColor,
        isDarkTheme
    ) {
        onTrigger = true
        when {
            isDynamicColor && colorScheme != null -> {
                ColorTuple(
                    colorScheme.primary,
                    colorScheme.secondary,
                    colorScheme.tertiary,
                    colorScheme.surface
                )
            }

            else -> colorTuple
        }
    }
}