package io.github.tarifchakder.materialcolor.quantize

/**
 * Creates a dictionary with keys of colors, and values of count of the color
 */
internal class QuantizerMap : Quantizer {

    private var colorToCount: Map<Int, Int>? = null

    override fun quantize(pixels: IntArray?, maxColors: Int): QuantizerResult {
        val pixelByCount: MutableMap<Int, Int> = LinkedHashMap()
        for (pixel in pixels!!) {
            val currentPixelCount = pixelByCount[pixel]
            val newPixelCount = if (currentPixelCount == null) 1 else currentPixelCount + 1
            pixelByCount[pixel] = newPixelCount
        }
        colorToCount = pixelByCount
        return QuantizerResult(pixelByCount)
    }
}
