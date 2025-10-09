package com.tarifchakder.materializekmp.materialcolor.scheme

import com.tarifchakder.materializekmp.materialcolor.dislike.DislikeAnalyzer
import com.tarifchakder.materializekmp.materialcolor.hct.Hct
import com.tarifchakder.materializekmp.materialcolor.palettes.TonalPalette
import com.tarifchakder.materializekmp.materialcolor.temperature.TemperatureCache
import kotlin.math.max

/**
 * A scheme that places the source color in Scheme.primaryContainer.
 *
 * Primary Container is the source color, adjusted for color relativity. It maintains constant
 * appearance in light mode and dark mode. This adds ~5 tone in light mode, and subtracts ~5 tone in
 * dark mode.
 *
 * Tertiary Container is the complement to the source color, using TemperatureCache. It also
 * maintains constant appearance.
 */
class SchemeFidelity(
    sourceColorHct: Hct,
    isDark: Boolean,
    contrastLevel: Double,
) : DynamicScheme(
    sourceColorHct = sourceColorHct,
    variant = Variant.FIDELITY,
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
        hct = DislikeAnalyzer.fixIfDisliked(TemperatureCache(sourceColorHct).complement),
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
