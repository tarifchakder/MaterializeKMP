package com.tarif.dynamictheme.colorpicker.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun AlphaColorBar(
    isAlphaBarVisible: Boolean,
    maxOpacityColor: Color,
    initialOpacityColor: Color,
    opacity: MutableState<Float>
) {
    if (isAlphaBarVisible) {
        Spacer(modifier = Modifier.height(16.dp))
        ColorSlider(colors = listOf(Color.Transparent, maxOpacityColor), selectedColor = initialOpacityColor) {
            opacity.value = it
        }
    }
}