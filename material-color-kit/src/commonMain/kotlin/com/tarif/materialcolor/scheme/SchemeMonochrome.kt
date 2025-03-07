package com.tarif.materialcolor.scheme

import com.tarif.materialcolor.hct.Hct
import com.tarif.materialcolor.palettes.TonalPalette

/**
 * A monochrome theme, colors are purely black / white / gray.
 */
class SchemeMonochrome(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.MONOCHROME,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 0.0),
    secondaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 0.0),
    tertiaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 0.0),
    neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 0.0),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 0.0),
)
