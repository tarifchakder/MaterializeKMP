package com.tarif.kmp.materialcolor.scheme

import com.tarif.kmp.materialcolor.hct.Hct
import com.tarif.kmp.materialcolor.palettes.TonalPalette
import com.tarif.kmp.materialcolor.utils.MathUtils

/**
 * A calm theme, sedated colors that aren't particularly chromatic.
 */
class SchemeTonalSpot(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.TONAL_SPOT,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 36.0),
    secondaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 16.0),
    tertiaryPalette = TonalPalette.fromHueAndChroma(
        hue = MathUtils.sanitizeDegrees(sourceColorHct.hue + 60.0),
        chroma = 24.0,
    ),
    neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 6.0),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 8.0),
)
