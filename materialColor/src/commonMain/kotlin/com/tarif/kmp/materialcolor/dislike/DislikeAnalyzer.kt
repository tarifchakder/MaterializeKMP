package com.tarif.kmp.materialcolor.dislike

import com.tarif.kmp.materialcolor.hct.Hct
import kotlin.math.round

/**
 * Check and/or fix universally disliked colors.
 *
 * Color science studies of color preference indicate universal distaste for dark yellow-greens,
 * and also show this is correlated to distate for biological waste and rotting food.
 *
 * See Palmer and Schloss, 2010 or Schloss and Palmer's Chapter 21 in Handbook of Color
 * Psychology (2015).
 */
object DislikeAnalyzer {

    /**
     * Returns true if color is disliked.
     *
     * Disliked is defined as a dark yellow-green that is not neutral.
     *
     * @param[hct] Hct color to check
     * @return true if color is disliked
     */
    fun isDisliked(hct: Hct): Boolean {
        val huePasses = round(hct.hue) in 90.0..111.0
        val chromaPasses: Boolean = round(hct.chroma) > 16.0
        val tonePasses: Boolean = round(hct.tone) < 65.0
        return huePasses && chromaPasses && tonePasses
    }

    /**
     * If color is disliked, lighten it to make it likable.
     *
     * @param[hct] Hct color to check
     * @return Lightened Hct color that is not disliked
     */
    fun fixIfDisliked(hct: Hct): Hct {
        return if (isDisliked(hct)) Hct.from(hct.hue, hct.chroma, 70.0) else hct
    }
}
