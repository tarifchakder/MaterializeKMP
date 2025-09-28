package com.tarifchakder.materializekmp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

/**
 * Represents the state of a dynamic theme, holding the current color seed or a set of color seeds used to generate the theme's color scheme.
 *
 * This class provides a way to manage and update the base colors for a dynamic Material theme. It stores a [ColorTuple]
 * which represents the primary, secondary, and tertiary seed colors. Changes to the `colorTuple` will trigger recomposition,
 * allowing the UI to dynamically adapt to the updated color scheme.
 *
 * @param initialColorTuple The initial [ColorTuple] containing the seed colors for generating the theme's color scheme.
 *                          This is used to set the initial state of the dynamic theme.
 */
@Stable
class DynamicThemeState internal constructor(initialColorTuple: ColorTuple) {

    /**
     * The current color seed tuple used for generating the dynamic theme.
     *
     * Changes to this state will trigger recomposition.
     */
    var colorTuple: MutableState<ColorTuple> = mutableStateOf(initialColorTuple)
        private set

    /**
     * Updates the primary color seed.
     *
     * @param newPrimaryColor The new primary color seed.
     */
    fun updatePrimaryColor(newPrimaryColor: Color) {
        colorTuple.value = colorTuple.value.copy(primary = newPrimaryColor)
    }

    /**
     * Updates the color seed tuple with a new set of colors.
     *
     * @param newColorSeedTuple The new [colorTuple] to use.
     */
    fun updateColorSeedTuple(newColorSeedTuple: ColorTuple) {
        colorTuple.value = newColorSeedTuple
    }

}