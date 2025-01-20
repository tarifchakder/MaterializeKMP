package com.tarif.dynamictheme

import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.tarif.dynamictheme.colortuple.ColorTuple
import com.tarif.dynamictheme.ktx.animateAllColors
import com.tarif.dynamictheme.provider.LocalDynamicThemeState

@Composable
fun getAppColorTuple(
    defaultColorTuple: ColorTuple,
    dynamicColor: Boolean,
    darkTheme: Boolean
): ColorTuple {
    val colorTuple: ColorTuple
    if (darkTheme) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }.run {
        colorTuple = ColorTuple(
            primary = primary,
            secondary = secondary,
            tertiary = tertiary,
            surface = surface
        )
    }
    return colorTuple
}

/**
 * DynamicTheme allows you to dynamically change the color scheme of the content hierarchy.
 * To do this you just need to update [DynamicThemeState].
 * @param state - current instance of [DynamicThemeState]
 * */
@Composable
fun DynamicTheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean,
    isAmoled: Boolean,
    colorTuple: ColorTuple,
    isInvertColors: Boolean,
    style: PaletteStyle,
    contrastLevel: Double,
    state: DynamicThemeState,
    typography: Typography = Typography(),
    content: @Composable () -> Unit,
) {
    val scheme = rememberDynamicColorScheme(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor ,
        isAmoled = isAmoled,
        isInvertColors = isInvertColors,
        colorTuple = state.colorTuple.value,
        style = style,
        contrastLevel = contrastLevel
    ).animateAllColors(tween(300))

    CompositionLocalProvider(
        values = arrayOf(LocalDynamicThemeState provides state),
        content = {
            MaterialTheme(
                typography = typography,
                colorScheme = scheme,
                content = content
            )
        }
    )
}





