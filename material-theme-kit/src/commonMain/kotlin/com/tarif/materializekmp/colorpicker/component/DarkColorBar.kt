package com.tarif.materializekmp.colorpicker.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun DarkColorBar(
    showDarkColorBar: Boolean,
    lightColor: Color,
    initialColor: Color,
    darkness: MutableState<Float>
) {
    if (showDarkColorBar) {
        Spacer(modifier = Modifier.height(16.dp))
        ColorSlider(colors = listOf(Color.Black, lightColor), selectedColor = initialColor) {
            darkness.value = 1 - it
        }
    }
}