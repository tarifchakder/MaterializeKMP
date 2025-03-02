package com.tarif.materialcolor.quantize

/**
 * An interface to allow use of different color spaces by quantizers.
 */
internal interface PointProvider {

    /**
     * The four components in the color space of an sRGB color.
     *
     * @param[argb] argb The ARGB (i.e. hex code) representation of this color.
     * @return The four components in the color space of an sRGB color.
     */
    fun fromInt(argb: Int): DoubleArray

    /**
     * The ARGB (i.e. hex code) representation of this color.
     *
     * @param[point] The four components in the color space of an sRGB color.
     * @return The ARGB (i.e. hex code) representation of this color.
     */
    fun toInt(point: DoubleArray): Int

    /**
     * Squared distance between two colors. Distance is defined by scientific color spaces and
     * referred to as delta E.
     *
     * @param[a] The first color.
     * @param[b] The second color.
     * @return The squared distance between the two colors.
     */
    fun distance(a: DoubleArray, b: DoubleArray): Double
}
