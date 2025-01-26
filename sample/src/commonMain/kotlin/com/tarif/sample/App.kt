package com.tarif.sample

import NotificationDuration
import Notify
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tarif.dynamictheme.DynamicTheme
import com.tarif.sample.component.SegmentedButtonColors
import com.tarif.sample.component.SegmentedButtonItem
import com.tarif.sample.component.SegmentedButtons
import com.tarif.sample.component.SegmentedButtonsDefaults
import kmp_material_theme.sample.generated.resources.Res
import kmp_material_theme.sample.generated.resources.ic_dark
import kmp_material_theme.sample.generated.resources.ic_light
import kmp_material_theme.sample.generated.resources.ic_system
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    var themeMode = remember { mutableStateOf(ThemeMode.SYSTEM) }
    var isDynamicColor = remember { mutableStateOf(true) }
    var isAmoled = remember { mutableStateOf(false) }
    var isInvertColor = remember { mutableStateOf(false) }
    var uriHandler = LocalUriHandler.current

    DynamicTheme(
        baseColor = Color.Magenta,
        isDarkTheme = themeMode.value.isDarkTheme(),
        isDynamicColor = isDynamicColor.value,
        isAmoled = isAmoled.value,
        isInvertColors = isInvertColor.value
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            Column {
                TopbarCardView(
                    modifier = Modifier.systemBarsPadding()
                        .padding(15.dp)
                        .widthIn(Dp.Unspecified, 450.dp)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                    themeMode = themeMode,
                    isDynamicColor = isDynamicColor,
                    isAmoled = isAmoled,
                    isInvertColor = isInvertColor
                )
            }
        }
    }

}

@Composable
private fun TopbarCardView(
    modifier: Modifier = Modifier,
    themeMode: MutableState<ThemeMode>,
    isDynamicColor: MutableState<Boolean>,
    isAmoled: MutableState<Boolean>,
    isInvertColor: MutableState<Boolean>
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            SegmentedButtons(
                modifier = Modifier.fillMaxWidth().height(40.dp),
                outlineThickness = 0.5.dp,
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onSurface)
            ) {
                SegmentedButtonItem(
                    selected = themeMode.value == ThemeMode.SYSTEM,
                    onClick = { themeMode.value = ThemeMode.SYSTEM },
                    colors = SegmentedButtonsDefaults.toButtonColors(),
                    icon = { SegmentButtonIcon(Res.drawable.ic_system) },
                    label = { SegmentButtonText("System") }
                )
                SegmentedButtonItem(
                    selected = themeMode.value == ThemeMode.LIGHT,
                    onClick = { themeMode.value = ThemeMode.LIGHT },
                    colors = SegmentedButtonsDefaults.toButtonColors(),
                    icon = { SegmentButtonIcon(Res.drawable.ic_light) },
                    label = { SegmentButtonText("Light") }
                )
                SegmentedButtonItem(
                    selected = themeMode.value == ThemeMode.DARK,
                    onClick = { themeMode.value = ThemeMode.DARK },
                    colors = SegmentedButtonsDefaults.toButtonColors(),
                    icon = { SegmentButtonIcon(Res.drawable.ic_dark) },
                    label = { SegmentButtonText("Dark") }
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 5.dp).align(Alignment.CenterHorizontally)
            ) {
                LabelledCheckBox(
                    checked = isDynamicColor.value,
                    label = "Dynamic color",
                    onCheckedChange = {
                        isDynamicColor.value = !isDynamicColor.value
                    }
                )
                LabelledCheckBox(
                    checked = isAmoled.value,
                    label = "Amoled",
                    onCheckedChange = {
                        isAmoled.value = !isAmoled.value
                    }
                )
                LabelledCheckBox(
                    checked = !isDynamicColor.value && isInvertColor.value,
                    label = "Invert color",
                    onCheckedChange = {
                        isInvertColor.value = (!isInvertColor.value && !isDynamicColor.value)
                        if (isDynamicColor.value) {
                            Notify(
                                message = "Can not change when dynamic color enabled",
                                duration = NotificationDuration.SHORT
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LabelledCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit) = {},
    label: String = "Dynamic Color",
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onCheckedChange(!checked) }
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp)
    ) {
        Checkbox(
            modifier = Modifier.size(20.dp),
            checked = checked,
            onCheckedChange = null
        )

        Spacer(Modifier.size(6.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400
        )
    }
}

@Preview
@Composable
private fun SegmentButtonIcon(drawableResource: DrawableResource) {
    Icon(
        painter = painterResource(drawableResource),
        contentDescription = "",
        modifier = Modifier.size(18.dp)
    )
}

@Composable
private fun SegmentButtonText(text: String) {
    Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.W400
    )
}

@Composable
private fun SegmentedButtonsDefaults.toButtonColors(): SegmentedButtonColors {
    return this.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = MaterialTheme.colorScheme.primary,
        outlineColor = MaterialTheme.colorScheme.onSurface
    )
}