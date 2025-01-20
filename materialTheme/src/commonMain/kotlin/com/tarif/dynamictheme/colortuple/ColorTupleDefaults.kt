package com.tarif.dynamictheme.colortuple

import androidx.compose.ui.graphics.Color
import com.tarif.dynamictheme.ktx.calculateSecondaryColor
import com.tarif.dynamictheme.ktx.calculateSurfaceColor
import com.tarif.dynamictheme.ktx.calculateTertiaryColor
import com.tarif.dynamictheme.ktx.toColor

val defaultColorTuples by lazy {
    listOf(
        Color(0xFFf8130d),
        Color(0xFF7a000b),
        Color(0xFF8a3a00),
        Color(0xFFff7900),
        Color(0xFFfcf721),
        Color(0xFF88dd20),
        Color(0xFF16B16E),
        Color(0xFF01a0a3),
        Color(0xFF005FFF),
        Color(0xFFfa64e1),
        Color(0xFFd7036a),
        Color(0xFFdb94fe),
        Color(0xFF7b2bec),
        Color(0xFF022b6d),
        Color(0xFFFFFFFF),
        Color(0xFF000000),
    ).map {
        ColorTuple(
            primary = it,
            secondary = it.calculateSecondaryColor().toColor(),
            tertiary = it.calculateTertiaryColor().toColor(),
            surface = it.calculateSurfaceColor().toColor()
        )
    }
}