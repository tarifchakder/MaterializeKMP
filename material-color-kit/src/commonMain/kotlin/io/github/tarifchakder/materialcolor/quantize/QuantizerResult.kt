package io.github.tarifchakder.materialcolor.quantize

import kotlin.jvm.JvmInline

/**
 * Represents result of a quantizer run
 */
@JvmInline
internal value class QuantizerResult(val colorToCount: Map<Int, Int>)
