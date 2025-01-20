package com.tarif.kmp.materialcolor.quantize

internal interface Quantizer {

    fun quantize(pixels: IntArray?, maxColors: Int): QuantizerResult?
}
