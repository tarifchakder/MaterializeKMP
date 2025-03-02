package com.tarif.dynamictheme.colorpicker

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tarif.dynamictheme.colorpicker.model.ColorPickerType
import com.tarif.dynamictheme.colorpicker.pickers.CircleColorPicker
import com.tarif.dynamictheme.colorpicker.pickers.ClassicColorPicker
import com.tarif.dynamictheme.colorpicker.pickers.RingColorPicker

/**
 * @param type Color picker type example [ColorPickerType.Circle].
 * @param onPickedColor Executes when the user selects a color.
 */
@ExperimentalComposeUiApi
@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    type: ColorPickerType = ColorPickerType.Classic(),
    onPickedColor: (Color) -> Unit
) {
    Box(modifier = modifier) {
        when (type) {
            is ColorPickerType.Classic -> ClassicColorPicker(
                pickerSize = type.colorPickerSize,
                isAlphaSliderVisible = type.isAlphaBarVisible,
                defaultColor = type.startColor,
                onNewColor = onPickedColor,
            )

            is ColorPickerType.Circle -> CircleColorPicker(
                showAlpha = type.isAlphaBarVisible,
                showBrightness = type.isBrightnessBarVisible,
                invertCenter = type.isLightCenter,
                initialColor = type.startColor,
                pickerSize = type.colorPickerSize,
                onColorChange = onPickedColor,

                )

            is ColorPickerType.Ring -> RingColorPicker(
                colorPickerSize = type.colorPickerSize,
                ringThickness = type.colorRingWidth,
                previewIndicatorRadius = type.previewCircleRadius,
                showLightnessControl = type.isLightnessBarVisible,
                showDarkColorBar = type.isDarknessBarVisible,
                showOpacityControl = type.isAlphaBarVisible,
                showPreviewIndicator = type.isColorPreviewVisible,
                seedColor = type.startColor,
                onColorChanged = onPickedColor
            )
        }
    }
}