package com.tarif.dynamictheme.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.roundToInt

internal fun DrawScope.drawColorSelector(color: Color, center: Offset) {
    drawCircle(color, radius = 18f, center = center)
    drawCircle(Color.White, radius = 18f, center = center, style = Stroke(5f))
    drawCircle(Color.LightGray, radius = 18f, center = center, style = Stroke(1f))
}

internal fun DrawScope.drawTransparentBackground(verticalBoxesSize: Int = 10) {
    val boxSize = size.height / verticalBoxesSize
    repeat((size.width / boxSize).roundToInt() + 1) { x ->
        repeat(verticalBoxesSize) { y ->
            drawRect(
                if ((y + x) % 2 == 0) {
                    Color.LightGray
                } else {
                    Color.White
                }, topLeft = Offset(x * boxSize, y * boxSize), size = Size(boxSize, boxSize)
            )
        }
    }
}

/**
 * Draw behind the composable a transparent effect background.
 * @param verticalBoxesAmount Amount of the white and gray boxes for a single column.
 */
fun Modifier.transparentBackground(verticalBoxesAmount: Int = 10) = this.drawBehind {
    drawTransparentBackground(verticalBoxesAmount)
}