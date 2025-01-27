package com.tarif.dynamictheme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

/**
 * State object that holds the current seed color of  material theme.
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