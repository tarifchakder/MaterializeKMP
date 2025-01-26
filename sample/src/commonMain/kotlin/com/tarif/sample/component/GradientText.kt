package com.tarif.sample.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun GradientText() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Red, Color.Yellow, Color.Yellow,Color.Cyan)
    )

    Text(
        text = "Gradient Text",
        style = TextStyle(
            fontSize = 40.sp,
            brush = gradientBrush
        )
    )
}
