package com.tarif.dynamictheme.remember

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.tarif.dynamictheme.ColorTuple
import com.tarif.dynamictheme.DynamicThemeState
import com.tarif.dynamictheme.provider.DynamicThemeStateSaver

/**
 * Creates and remembers a [DynamicThemeState] instance.
 *
 * This function provides a convenient way to create and retain a [DynamicThemeState] across recompositions
 * and configuration changes (like screen rotation). It uses `rememberSaveable` to achieve this persistence.
 *
 * The [DynamicThemeState] holds the dynamic color scheme for the application, allowing for customization
 * and adaptation of the theme's primary, secondary, tertiary, and surface colors.
 *
 * @param initialColorTuple An optional [ColorTuple] representing the initial color values for the theme.
 *                          If not provided, it defaults to a [ColorTuple] created from the current
 *                          [MaterialTheme.colorScheme]'s primary, secondary, tertiary, and surface colors.
 *                          This allows you to start with the default Material Theme colors or specify custom
 *                          initial colors.
 * @return A [DynamicThemeState] instance that is remembered and potentially saved across configuration changes.
 *
 * @see DynamicThemeState
 * @see ColorTuple
 * @see MaterialTheme
 * @see rememberSaveable
 */
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