package com.tarif.materialcolor.quantize

internal interface Quantizer {

    fun quantize(pixels: IntArray?, maxColors: Int): QuantizerResult?
}
