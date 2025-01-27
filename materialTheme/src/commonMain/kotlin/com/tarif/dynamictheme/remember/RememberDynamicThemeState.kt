package com.tarif.dynamictheme.remember

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.tarif.dynamictheme.ColorTuple
import com.tarif.dynamictheme.DynamicThemeState
import com.tarif.dynamictheme.provider.DynamicThemeStateSaver

/**
 * Creates and remember [DynamicThemeState] instance
 * */
@Composable
fun rememberThemeState(
    initialColorTuple: ColorTuple = ColorTuple(
        primary = MaterialTheme.colorScheme.primary,
        secondary = MaterialTheme.colorScheme.secondary,
        tertiary = MaterialTheme.colorScheme.tertiary,
        surface = MaterialTheme.colorScheme.surface
    )
): DynamicThemeState {
    return rememberSaveable(saver = DynamicThemeStateSaver) {
        DynamicThemeState(initialColorTuple)
    }
}