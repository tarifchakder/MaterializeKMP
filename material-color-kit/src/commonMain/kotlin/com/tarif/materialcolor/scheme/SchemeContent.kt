package com.tarif.materialcolor.scheme

import com.tarif.materialcolor.dislike.DislikeAnalyzer
import com.tarif.materialcolor.hct.Hct
import com.tarif.materialcolor.palettes.TonalPalette
import com.tarif.materialcolor.temperature.TemperatureCache
import kotlin.math.max

/**
 * A scheme that places the source color in Scheme.primaryContainer.
 *
 * Primary Container is the source color, adjusted for color relativity. It maintains constant
 * appearance in light mode and dark mode. This adds ~5 tone in light mode, and subtracts ~5 tone in
 * dark mode.
 *
 * Tertiary Container is an analogous color, specifically, the analog of a color wheel divided
 * into 6, and the precise analog is the one found by increasing hue. This is a scientifically
 * grounded equivalent to rotating hue clockwise by 60 degrees. It also maintains constant
 * appearance.
 */
class SchemeContent(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.CONTENT,
    isDark = isDark,
    contrastLevel = contrastLevel,
    primaryPalette = TonalPalette.fromHueAndChroma(
        hue = sourceColorHct.hue,
        chroma = sourceColorHct.chroma,
    ),
    secondaryPalette = TonalPalette.fromHueAndChroma(
        hue = sourceColorHct.hue,
        chroma = max(sourceColorHct.chroma - 32.0, sourceColorHct.chroma * 0.5),
    ),
    tertiaryPalette = TonalPalette.fromHct(
        hct = DislikeAnalyzer.fixIfDisliked(
            hct = TemperatureCache(sourceColorHct).getAnalogousColors(count = 3, divisions = 6)[2],
        ),
    ),
    neutralPalette = TonalPalette.fromHueAndChroma(
        hue = sourceColorHct.hue,
        chroma = sourceColorHct.chroma / 8.0,
    ),
    neutralVariantPalette = TonalPalette.fromHueAndChroma(
        hue = sourceColorHct.hue,
        chroma = sourceColorHct.chroma / 8.0 + 4.0,
    ),
)
