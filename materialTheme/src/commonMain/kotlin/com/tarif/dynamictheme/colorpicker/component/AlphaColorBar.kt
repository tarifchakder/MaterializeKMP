package com.tarif.dynamictheme.colorpicker.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tarif.dynamictheme.colorpicker.pickers.ColorSlider

@Composable
internal fun AlphaColorBar(
    showAlphaBar: Boolean,
    rangeColor: Color,
    initialColor: Color,
    alpha: MutableState<Float>
) {
    if (showAlphaBar) {
        Spacer(modifier = Modifier.height(16.dp))
        ColorSlider(colors = listOf(Color.Transparent, rangeColor), initialColor = initialColor) {
            alpha.value = it
        }
    }
}