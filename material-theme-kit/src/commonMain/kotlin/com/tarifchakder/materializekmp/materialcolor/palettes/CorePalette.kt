package com.tarifchakder.materializekmp.materialcolor.palettes

import com.tarifchakder.materializekmp.materialcolor.hct.Hct
import kotlin.math.max
import kotlin.math.min

/**
 * An intermediate concept between the key color for a UI theme, and a full color scheme. 5 sets of
 * tones are generated, all except one use the same hue as the key color, and all vary in chroma.
 *
 * @constructor Create a new CorePalette
 * @param[argb] ARGB representation of a color
 * @param[isContent] Whether the color is used as a content color
 */
class CorePalette private constructor(argb: Int, isContent: Boolean) {

    val a1: TonalPalette
    val a2: TonalPalette
    val a3: TonalPalette
    val n1: TonalPalette
    val n2: TonalPalette
    val error: TonalPalette

    init {
        val hct = Hct.fromInt(argb)
        val hue = hct.hue
        val chroma = hct.chroma
        if (isContent) {
            a1 = TonalPalette.fromHueAndChroma(hue = hue, chroma = chroma)
            a2 = TonalPalette.fromHueAndChroma(hue = hue, chroma = chroma / 3.0)
            a3 = TonalPalette.fromHueAndChroma(hue = hue + 60.0, chroma = chroma / 2.0)
            n1 = TonalPalette.fromHueAndChroma(hue = hue, chroma = min(chroma / 12.0, 4.0))
            n2 = TonalPalette.fromHueAndChroma(hue = hue, chroma = min(chroma / 6.0, 8.0))
        } else {
            a1 = TonalPalette.fromHueAndChroma(hue = hue, chroma = max(48.0, chroma))
            a2 = TonalPalette.fromHueAndChroma(hue = hue, chroma = 16.0)
            a3 = TonalPalette.fromHueAndChroma(hue = hue + 60.0, chroma = 24.0)
            n1 = TonalPalette.fromHueAndChroma(hue = hue, chroma = 4.0)
            n2 = TonalPalette.fromHueAndChroma(hue = hue, chroma = 8.0)
        }
        error = TonalPalette.fromHueAndChroma(hue = 25.0, chroma = 84.0)
    }

    public companion object {

        /**
         * Create key tones from a color.
         *
         * @param argb ARGB representation of a color
         */
        fun of(argb: Int): CorePalette = CorePalette(argb = argb, isContent = false)

        /**
         * Create content key tones from a color.
         *
         * @param argb ARGB representation of a color
         */
        fun contentOf(argb: Int): CorePalette = CorePalette(argb = argb, isContent = true)
    }
}
