package com.tarif.dynamictheme.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tarif.dynamictheme.colorpicker.model.ColorPickerType
import com.tarif.dynamictheme.colorpicker.pickers.CircleColorPicker
import com.tarif.dynamictheme.colorpicker.pickers.ClassicColorPicker
import com.tarif.dynamictheme.colorpicker.pickers.RingColorPicker
import com.tarif.dynamictheme.extension.toHex
import com.tarif.dynamictheme.extension.transparentBackground

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
                showAlphaBar = type.showAlphaBar,
                initialColor = type.initialColor,
                size = type.colorPickerSize,
                onPickedColor = onPickedColor,
            )

            is ColorPickerType.Circle -> CircleColorPicker(
                showAlphaBar = type.showAlphaBar,
                showBrightnessBar = type.showBrightnessBar,
                lightCenter = type.lightCenter,
                initialColor = type.initialColor,
                colorPickerSize = type.colorPickerSize,
                onPickedColor = onPickedColor,

                )

            is ColorPickerType.Ring -> RingColorPicker(
                size = type.colorPickerSize,
                ringWidth = type.ringWidth,
                previewRadius = type.previewRadius,
                showLightColorBar = type.showLightnessBar,
                showDarkColorBar = type.showDarknessBar,
                showAlphaBar = type.showAlphaBar,
                showColorPreview = type.showColorPreview,
                initialColor = type.initialColor,
                onPickedColor = onPickedColor
            )
        }
    }
}

/**
 * @param show Dialog Visibility.
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param properties [DialogProperties] for further customization of this dialog's behavior.
 * @param type Color picker type example [ColorPickerType.Classic].
 * @param onPickedColor Executes when the user selects a color from the color picker dialog.
 */
@ExperimentalComposeUiApi
@Composable
fun ColorPickerDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    type: ColorPickerType = ColorPickerType.Classic(),
    onPickedColor: (Color) -> Unit
) {
    var showDialog by remember(show) {
        mutableStateOf(show)
    }
    var color by remember {
        mutableStateOf(Color.White)
    }
    if (showDialog) {
        Dialog(onDismissRequest = {
            onDismissRequest()
            showDialog = false
        }, properties = properties) {
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.padding(32.dp)) {
                    Column {
                        ColorPicker(type = type, onPickedColor = {
                            color = it
                        })
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp, 30.dp)
                                    .clip(RoundedCornerShape(50))
                                    .border(0.3.dp, Color.LightGray, RoundedCornerShape(50))
                                    .transparentBackground(verticalBoxesAmount = 4)
                                    .background(color)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(color = Color.Gray)) {
                                        append("#")
                                    }
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(color.toHex())
                                    }
                                },
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Monospace,
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onPickedColor(color)
                                showDialog = false
                            },
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(text = "Select")
                        }
                    }
                }
            }
        }
    }
}