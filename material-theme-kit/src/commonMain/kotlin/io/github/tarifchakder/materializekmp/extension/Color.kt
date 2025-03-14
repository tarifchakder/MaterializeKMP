package io.github.tarifchakder.materializekmp.extension

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.github.tarifchakder.materialcolor.hct.Hct
import io.github.tarifchakder.materialcolor.palettes.TonalPalette
import kotlin.math.roundToInt

fun Int.toColor() = Color(this)

@Composable
fun Color.harmonizeWithPrimary(
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
): Color = blend(MaterialTheme.colorScheme.primary, fraction)

fun Color.calculateSecondaryColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue, chroma / 3.0).tone(80)
}

fun Color.calculateTertiaryColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue + 60.0, chroma / 2.0).tone(80)
}

fun Color.calculateSurfaceColor(): Int {
    val hct = Hct.fromInt(this.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    return TonalPalette.fromHueAndChroma(hue, (chroma / 12.0).coerceAtMost(4.0)).tone(90)
}

/**
 * Returns an integer array for all color channels value.
 */
fun Color.argb(): Array<Int> {
    val argb = toArgb()
    val alpha = argb shr 24 and 0xff
    val red = argb shr 16 and 0xff
    val green = argb shr 8 and 0xff
    val blue = argb and 0xff
    return arrayOf(alpha, red, green, blue)
}

/**
 * Returns the red value as an integer.
 */
fun Color.red(): Int {
    return toArgb() shr 16 and 0xff
}

/**
 * Returns the green value as an integer.
 */
fun Color.green(): Int {
    return toArgb() shr 8 and 0xff
}

/**
 * Returns the blue value as an integer.
 */
fun Color.blue(): Int {
    return toArgb() and 0xff
}

/**
 * Returns the alpha value as an integer.
 */
fun Color.alpha(): Int {
    return toArgb() shr 24 and 0xff
}

/**
 * Returns ARGB color as a hex string.
 * @param hexPrefix Add # char before the hex number.
 * @param includeAlpha Include the alpha value within the hex string.
 */
fun Color.toHex(hexPrefix: Boolean = false, includeAlpha: Boolean = true): String {
    val (alpha, red, green, blue) = argb()
    return buildString {
        if (hexPrefix) {
            append("#")
        }
        if (includeAlpha) {
            append(alpha.toHex())
        }
        append(red.toHex())
        append(green.toHex())
        append(blue.toHex())
    }
}

fun colorToHSV(color: Color): FloatArray {
    val r = color.red
    val g = color.green
    val b = color.blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    val h = when {
        delta == 0f -> 0f
        max == r -> ((g - b) / delta) % 6
        max == g -> ((b - r) / delta) + 2
        else -> ((r - g) / delta) + 4
    } * 60

    val hue = if (h < 0) h + 360 else h
    val saturation = if (max == 0f) 0f else delta / max
    val brightness = max

    return floatArrayOf(hue, saturation, brightness)
}

private fun Int.toHex(): String {
    return this.toString(16).let {
        if (it.length == 1) {
            "0$it"
        } else {
            it
        }
    }
}

internal fun Double.lighten(lightness: Float): Double {
    return (this + (255 - this) * lightness).coerceIn(0.0, 255.0)
}

internal fun Float.lighten(lightness: Float): Float {
    return this + (255 - this) * lightness
}

internal fun Int.lighten(lightness: Float): Int {
    val newValue = (this + (255 - this) * lightness).coerceIn(0f, 255f)
    return newValue.roundToInt()
}

internal fun Double.darken(darkness: Float): Double {
    return this - this * darkness
}

internal fun Float.darken(darkness: Float): Float {
    return this - this * darkness
}

internal fun Int.darken(darkness: Float): Int {
    return (this - this * darkness).roundToInt()
}

/**
 * Blend between two ARGB colors using the given ratio.
 *
 * <p>A blend ratio of 0.0 will result in {@code color1}, 0.5 will give an even blend,
 * 1.0 will result in {@code color2}.</p>
 *
 * @param color1 the first ARGB color
 * @param color2 the second ARGB color
 * @param ratio the blend ratio of {@code color1} to {@code color2}
 *
 * Ensure the fraction is between 0 and 1
 */
@ColorInt
fun blendARGB(
    @ColorInt color1: Int, @ColorInt color2: Int,
    @FloatRange(from = 0.0, to = 1.0) ratio: Float
): Int {
    val normalizedFraction = ratio.coerceIn(0f, 1f)

    // Extract RGBA components from color1
    val r1 = Color(color1).red
    val g1 = Color(color1).green
    val b1 = Color(color1).blue
    val a1 = Color(color1).alpha

    // Extract RGBA components from color2
    val r2 = Color(color2).red
    val g2 = Color(color2).green
    val b2 = Color(color2).blue
    val a2 = Color(color2).alpha

    // Blend the alpha component
    val blendedAlpha = (a1 * (1 - normalizedFraction) + a2 * normalizedFraction).toInt()

    // Blend the RGB components
    val blendedRed = (r1 * (1 - normalizedFraction) + r2 * normalizedFraction).toInt()
    val blendedGreen = (g1 * (1 - normalizedFraction) + g2 * normalizedFraction).toInt()
    val blendedBlue = (b1 * (1 - normalizedFraction) + b2 * normalizedFraction).toInt()

    // Return the composed color
    return Color(blendedRed, blendedGreen, blendedBlue, blendedAlpha).toArgb()
}