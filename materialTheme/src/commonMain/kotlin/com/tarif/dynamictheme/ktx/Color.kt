package com.tarif.dynamictheme.ktx

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tarif.kmp.materialcolor.hct.Hct
import com.tarif.kmp.materialcolor.palettes.TonalPalette

fun Int.toColor() = Color(this)

@Composable
fun Color.harmonizeWithPrimary(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
): Color = blend(MaterialTheme.colorScheme.primary, fraction)

fun Color.calculateSecondaryColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue, chroma / 3.0).tone(80)
}

fun Color.calculateTertiaryColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue + 60.0, chroma / 2.0).tone(80)
}

fun Color.calculateSurfaceColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue, (chroma / 12.0).coerceAtMost(4.0)).tone(90)
}

inline val Green: Color
    @Composable get() = Color(0xFFBADB94).harmonizeWithPrimary(0.2f)

inline val Blue: Color
    @Composable get() = Color(0xFF0088CC).harmonizeWithPrimary(0.2f)

inline val Black: Color
    @Composable get() = Color(0xFF142329).harmonizeWithPrimary(0.2f)

inline val White: Color
    @Composable get() = Color(0xFFFFFFFF).harmonizeWithPrimary(0.05f)

inline val BitcoinColor: Color
    @Composable get() = Color(0xFFF7931A).harmonizeWithPrimary(0.2f)

inline val USDTColor: Color
    @Composable get() = Color(0xFF50AF95).harmonizeWithPrimary(0.2f)

inline val TONSpaceColor: Color
    @Composable get() = Color(0xFF232328).harmonizeWithPrimary(0.2f)

inline val TONColor: Color
    @Composable get() = Color(0xFF0098EA).harmonizeWithPrimary(0.2f)