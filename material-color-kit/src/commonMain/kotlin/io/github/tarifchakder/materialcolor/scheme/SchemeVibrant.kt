package io.github.tarifchakder.materialcolor.scheme

import io.github.tarifchakder.materialcolor.hct.Hct
import io.github.tarifchakder.materialcolor.palettes.TonalPalette

/**
 * A loud theme, colorfulness is maximum for Primary palette, increased for others.
 */
class SchemeVibrant(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.VIBRANT,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 200.0),
    secondaryPalette = TonalPalette.fromHueAndChroma(
        hue = getRotatedHue(sourceColorHct, HUES, SECONDARY_ROTATIONS),
        chroma = 24.0,
    ),
    tertiaryPalette = TonalPalette.fromHueAndChroma(
        hue = getRotatedHue(sourceColorHct, HUES, TERTIARY_ROTATIONS),
        chroma = 32.0,
    ),
    neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 10.0),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.hue, chroma = 12.0),
) {

    private companion object {

        private val HUES =
            doubleArrayOf(0.0, 41.0, 61.0, 101.0, 131.0, 181.0, 251.0, 301.0, 360.0)

        private val SECONDARY_ROTATIONS =
            doubleArrayOf(18.0, 15.0, 10.0, 12.0, 15.0, 18.0, 15.0, 12.0, 12.0)

        private val TERTIARY_ROTATIONS =
            doubleArrayOf(35.0, 30.0, 20.0, 25.0, 30.0, 35.0, 30.0, 25.0, 25.0)
    }
}
