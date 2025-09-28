package com.tarifchakder.materializekmp.materialcolor.quantize

internal interface Quantizer {

    fun quantize(pixels: IntArray?, maxColors: Int): QuantizerResult?
}
