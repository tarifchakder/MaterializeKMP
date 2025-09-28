package com.tarifchakder.materializekmp.materialcolor.dynamiccolor

import com.tarifchakder.materializekmp.materialcolor.utils.MathUtils.lerp

/**
 * A class containing a value that changes with the contrast level.
 *
 * Usually represents the contrast requirements for a dynamic color on its background. The four
 * values correspond to values for contrast levels -1.0, 0.0, 0.5, and 1.0 respectively.
 *
 * Creates a `ContrastCurve` object.
 *
 * @param low Value for contrast level -1.0
 * @param normal Value for contrast level 0.0
 * @param medium Value for contrast level 0.5
 * @param high Value for contrast level 1.0
 */
class ContrastCurve(
    private val low: Double,
    private val normal: Double,
    private val medium: Double,
    private val high: Double,
) {

    /**
     * Returns the value at a given contrast level.
     *
     * @param contrastLevel The contrast ratio. 0.0 is the default (normal); -1.0 is the lowest; 1.0
     * is the highest.
     * @return The value. For contrast ratios, a number between 1.0 and 21.0.
     */
    fun get(contrastLevel: Double): Double = when {
        contrastLevel <= -1.0 -> low
        contrastLevel < 0.0 -> lerp(low, normal, (contrastLevel - -1) / 1)
        contrastLevel < 0.5 -> lerp(normal, medium, (contrastLevel - 0) / 0.5)
        contrastLevel < 1.0 -> lerp(medium, high, (contrastLevel - 0.5) / 0.5)
        else -> high
    }
}
