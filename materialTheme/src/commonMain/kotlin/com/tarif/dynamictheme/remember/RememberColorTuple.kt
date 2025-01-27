package com.tarif.dynamictheme.remember

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tarif.dynamictheme.ColorTuple
import com.tarif.dynamictheme.getColorScheme
import com.tarif.dynamictheme.getObserveAsState

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