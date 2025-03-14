package io.github.tarifchakder.materializekmp.colorpicker.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun LightColorBar(
    showLightColorBar: Boolean,
    selectedColor: Color,
    initialColor: Color,
    lightness: MutableState<Float>
) {
    if (showLightColorBar) {
        Spacer(modifier = Modifier.height(16.dp))
        ColorSlider(
            colors = listOf(Color.White, selectedColor),
            selectedColor=initialColor
        ) {
            lightness.value = 1 - it
        }
    }
}