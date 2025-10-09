package com.tarifchakder.materializekmp.colorpicker.model

/**
 * Represents a range of colors in the RGB color space, defined by a start and end color.
 * Each range transitions smoothly between the specified start and end colors.
 *
 * The color values are represented as 32-bit integers in ARGB format (0xAARRGGBB),
 * but only the RGB components are used for defining the range.
 *
 * This sealed class provides a set of predefined color ranges and utilities to
 * navigate between them.
 *
 * @property rangeStart The starting color of the range, represented as an ARGB integer.
 * @property rangeEnd The ending color of the range, represented as an ARGB integer.
 */
sealed class ColorRange(private val rangeStart: Int, private val rangeEnd: Int) {
    data object RedToYellow : ColorRange(0xFFFF0000.toInt(), 0xFFFFFF00.toInt())
    data object YellowToGreen : ColorRange(0xFFFFFF00.toInt(), 0xFF00FF00.toInt())
    data object GreenToCyan : ColorRange(0xFF00FF00.toInt(), 0xFF00FFFF.toInt())
    data object CyanToBlue : ColorRange(0xFF00FFFF.toInt(), 0xFF0000FF.toInt())
    data object BlueToMagenta : ColorRange(0xFF0000FF.toInt(), 0xFFFF00FF.toInt())
    data object MagentaToRed : ColorRange(0xFFFF00FF.toInt(), 0xFFFF0000.toInt())

    companion object {
        val entries = listOf(RedToYellow, YellowToGreen, GreenToCyan, CyanToBlue, BlueToMagenta, MagentaToRed)
    }

    fun next(): ColorRange {
        val rangeIndex = entries.indexOf(this)
        return entries[(rangeIndex + 1) % entries.size]
    }
}