package io.github.tarifchakder.materialcolor.scheme

import io.github.tarifchakder.materialcolor.hct.Hct
import io.github.tarifchakder.materialcolor.palettes.TonalPalette

/**
 * A theme that's slightly more chromatic than monochrome, which is purely black / white / gray.
 */
class SchemeNeutral(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.NEUTRAL,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 12.0),
    secondaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 8.0),
    tertiaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 16.0),
    neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 2.0),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 2.0),
)
