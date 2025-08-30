package io.github.tarifchakder.materializekmp.materialcolor.scheme

import io.github.tarifchakder.materializekmp.materialcolor.hct.Hct
import io.github.tarifchakder.materializekmp.materialcolor.palettes.TonalPalette
import io.github.tarifchakder.materializekmp.materialcolor.utils.MathUtils

/**
 * Provides important settings for creating colors dynamically, and 6 color palettes. Requires: 1. A
 * color. (source color) 2. A theme. (Variant) 3. Whether or not its dark mode. 4. Contrast level.
 * (-1 to 1, currently contrast ratio 3.0 and 7.0)
 */
open class DynamicScheme(
    val sourceColorHct: Hct,
    val variant: Variant,
    val isDark: Boolean,
    val contrastLevel: Double,
    val primaryPalette: TonalPalette,
    val secondaryPalette: TonalPalette,
    val tertiaryPalette: TonalPalette,
    val neutralPalette: TonalPalette,
    val neutralVariantPalette: TonalPalette,
    val errorPalette: TonalPalette = TonalPalette.fromHueAndChroma(hue = 25.0, chroma = 84.0),
) {

    val sourceColorArgb: Int = sourceColorHct.toInt()

    public companion object {

        /**
         * Given a set of hues and set of hue rotations, locate which hues the source color's hue is
         * between, apply the rotation at the same index as the first hue in the range, and return the
         * rotated hue.
         *
         * @param sourceColorHct The color whose hue should be rotated.
         * @param hues A set of hues.
         * @param rotations A set of hue rotations.
         * @return Color's hue with a rotation applied.
         */
        fun getRotatedHue(
            sourceColorHct: Hct,
            hues: DoubleArray,
            rotations: DoubleArray,
        ): Double {
            val sourceHue: Double = sourceColorHct.hue
            if (rotations.size == 1) {
                return MathUtils.sanitizeDegrees(sourceHue + rotations[0])
            }

            val size = hues.size
            for (i in 0..size - 2) {
                val thisHue = hues[i]
                val nextHue = hues[i + 1]
                if (thisHue < sourceHue && sourceHue < nextHue) {
                    return MathUtils.sanitizeDegrees(sourceHue + rotations[i])
                }
            }

            // If this statement executes, something is wrong, there should have been a rotation
            // found using the arrays.
            return sourceHue
        }
    }
}
