package com.tarif.dynamictheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import com.tarif.dynamictheme.colortuple.ColorTuple
import com.tarif.dynamictheme.provider.DynamicThemeStateSaver

/**
 * State object that holds the current values for a dynamic material theme.
 *
 * Use [rememberDynamicThemeState] to create an instance of this class.
 *
 * @param[initialColorTuple] The data class containing some basic color required for generating material color
 */
@Stable
class DynamicThemeState internal constructor(initialColorTuple: ColorTuple) {

    val colorTuple: MutableState<ColorTuple> = mutableStateOf(initialColorTuple)

    fun updateColor(color: Color) {
        colorTuple.value = ColorTuple(
            primary = color,
            secondary = null,
            tertiary = null
        )
    }

    fun updateColorTuple(newColorTuple: ColorTuple) {
        colorTuple.value = newColorTuple
    }

}

/**
 * Creates and remember [DynamicThemeState] instance
 * */
@Composable
fun rememberDynamicThemeState(
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