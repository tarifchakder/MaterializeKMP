package com.tarif.materialcolor.scheme

import com.tarif.materialcolor.hct.Hct
import com.tarif.materialcolor.palettes.TonalPalette
import com.tarif.materialcolor.utils.MathUtils

/**
 * A playful theme - the source color's hue does not appear in the theme.
 */
class SchemeFruitSalad(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.FRUIT_SALAD,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(
        hue = MathUtils.sanitizeDegrees(sourceColorHct.hue - 50.0),
        chroma = 48.0,
    ),
    secondaryPalette = TonalPalette.fromHueAndChroma(
        hue = MathUtils.sanitizeDegrees(sourceColorHct.hue - 50.0),
        chroma = 36.0,
    ),
    tertiaryPalette = TonalPalette.fromHueAndChroma(
        hue = sourceColorHct.hue,
        chroma = 36.0,
    ),
    neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, 10.0),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, 16.0),
)