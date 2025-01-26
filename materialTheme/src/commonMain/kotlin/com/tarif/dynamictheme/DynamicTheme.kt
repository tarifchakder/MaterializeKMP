package com.tarif.dynamictheme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.tarif.dynamictheme.colortuple.ColorTuple
import com.tarif.dynamictheme.colortuple.rememberColorTuple
import com.tarif.dynamictheme.ktx.animateAllColors
import com.tarif.dynamictheme.provider.LocalDynamicThemeState


/**
 * A Dynamic Material Theme that adapts to the given base color or primary color
 *
 * You can access the current base color via [LocalDynamicThemeState].
 * remember [base color == primary color] follow class [ColorTuple]
 *
 * @see rememberDynamicColorScheme
 * @see PaletteStyle
 * @param[baseColor] The base color to use for generating the color scheme.
 * @param[isDarkTheme] Whether to use a dark theme or not.
 * @param[isDynamicColor] Whether device support dynamic color
 * @param[isAmoled] Whether the dark scheme is used with Amoled screen (Pure dark).
 * @param[isInvertColors] It will invert colorscheme but if [isDynamicColor] enabled it will not work
 * @param[style] The style of the color scheme.
 * @param[contrastLevel] The contrast level of the color scheme.
 * @param[isAnimateColorScheme] Whether to animate the color scheme or not.
 * @param[animationSpec] The animation spec to use for animating the color scheme.
 * @param[content] The Composable content of the theme.
 */
@Composable
fun DynamicTheme(
    baseColor : Color,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    isAmoled: Boolean = false,
    isInvertColors: Boolean = false,
    style: PaletteStyle = PaletteStyle.TonalSpot,
    contrastLevel: Double = 0.00,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = Typography(),
    isAnimateColorScheme: Boolean = true,
    animationSpec: AnimationSpec<Color> = tween(300),
    content: @Composable () -> Unit,
) {
    val defaultColorTuple = rememberColorTuple(
        colorTuple = ColorTuple(primary = baseColor),
        isDynamicColor = isDynamicColor,
        isDarkTheme = isDarkTheme
    )

    val themeState = rememberDynamicThemeState(initialColorTuple = defaultColorTuple)

    val scheme = rememberDynamicColorScheme(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor ,
        isAmoled = isAmoled,
        isInvertColors = isInvertColors,
        colorTuple = themeState.colorTuple.value,
        style = style,
        contrastLevel = contrastLevel
    ).animateAllColors(
        isAnimateColorScheme = isAnimateColorScheme,
        animationSpec = animationSpec
    )

    LaunchedEffect(defaultColorTuple){
        themeState.updateColorTuple(defaultColorTuple)
    }

    CompositionLocalProvider(LocalDynamicThemeState provides themeState) {
        MaterialTheme(
            colorScheme = scheme,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }

}

/**
 * A Dynamic Material Theme that adapts to the given [ColorTuple]
 *
 * for more details follow [ColorTuple] class where primary color as base color
 * other colors is optional
 *
 * You can access the current base color via [LocalDynamicThemeState].
 *
 * @see rememberDynamicColorScheme
 * @see PaletteStyle
 * @param[colorTuple] The data class containing some basic color required for generating material color
 * @param[isDarkTheme] Whether to use a dark theme or not.
 * @param[isDynamicColor] Whether device support dynamic color
 * @param[isAmoled] Whether the dark scheme is used with Amoled screen (Pure dark).
 * @param[isInvertColors] It will invert colorscheme but if [isDynamicColor] enabled it will not work
 * @param[style] The style of the color scheme.
 * @param[contrastLevel] The contrast level of the color scheme.
 * @param[isAnimateColorScheme] Whether to animate the color scheme or not.
 * @param[animationSpec] The animation spec to use for animating the color scheme.
 * @param[content] The Composable content of the theme.
 */
@Composable
fun DynamicTheme(
    colorTuple: ColorTuple,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    isAmoled: Boolean = false,
    isInvertColors: Boolean = false,
    style: PaletteStyle = PaletteStyle.TonalSpot,
    contrastLevel: Double = 0.00,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = Typography(),
    isAnimateColorScheme: Boolean = true,
    animationSpec: AnimationSpec<Color> = tween(300),
    content: @Composable () -> Unit,
) {
   val defaultColorTuple = rememberColorTuple(
        colorTuple = colorTuple,
        isDynamicColor = isDynamicColor,
        isDarkTheme = isDarkTheme
    )

    val themeState = rememberDynamicThemeState(initialColorTuple = defaultColorTuple )

    val scheme = rememberDynamicColorScheme(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor ,
        isAmoled = isAmoled,
        isInvertColors = isInvertColors,
        colorTuple = themeState.colorTuple.value,
        style = style,
        contrastLevel = contrastLevel
    ).animateAllColors(
        isAnimateColorScheme = isAnimateColorScheme,
        animationSpec = animationSpec
    )

    LaunchedEffect(defaultColorTuple){
        themeState.updateColorTuple(defaultColorTuple)
    }

    CompositionLocalProvider(LocalDynamicThemeState provides themeState) {
        MaterialTheme(
            colorScheme = scheme,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }

}





