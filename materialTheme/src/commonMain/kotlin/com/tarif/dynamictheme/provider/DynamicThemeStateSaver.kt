package com.tarif.dynamictheme.provider

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tarif.dynamictheme.DynamicThemeState
import com.tarif.dynamictheme.colortuple.ColorTuple

val DynamicThemeStateSaver: Saver<DynamicThemeState, String> = Saver(
    save = {
        val colorTuple = it.colorTuple.value
        "${colorTuple.primary.toArgb()}*${colorTuple.secondary?.toArgb()}*${colorTuple.tertiary?.toArgb()}*${colorTuple.surface?.toArgb()}"
    },
    restore = { string ->
        val ar = string.split("*").map { s -> s.toIntOrNull()?.let { Color(it) } }
        DynamicThemeState(
            initialColorTuple = ColorTuple(
                ar[0]!!,
                ar[1],
                ar[2],
                ar[3]
            )
        )
    }
)