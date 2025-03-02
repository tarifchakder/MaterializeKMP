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
import com.tarif.dynamictheme.extension.animateAllColors
import com.tarif.dynamictheme.provider.LocalDynamicThemeState
import com.tarif.dynamictheme.remember.rememberColorScheme
import com.tarif.dynamictheme.remember.rememberColorTuple
import com.tarif.dynamictheme.remember.rememberThemeState


/**
- * **DynamicTheme:** A highly customizable Material Design 3 theme that dynamically adapts its
- * color scheme based on a provided base color, the user's system preferences, and optional
- * device features.
- *
- * This composable provides a flexible way to create a visually appealing and adaptable user
- * interface. It leverages Material You's dynamic color capabilities when available, falling
- * back to a static, generated color scheme when not supported.
- *
- * **Key Features:**
- *
- * *   **Base Color Adaptation:** Generates a full color scheme (light and dark) derived from
- *     a single `seedColor` (which serves as the primary color seed).
- * *   **Dynamic Color (Material You):** Extracts colors from the user's wallpaper to create a
- *     dynamic color scheme when `isDynamicColor` is true and the device supports it. This
- *     feature provides a personalized and cohesive experience that integrates with the user's
- *     device.
- * *   **Dark/Light Modes:** Automatically switches between dark and light color schemes based
- *     on the `isDarkTheme` parameter or the system's current dark mode setting.
- * *   **AMOLED Mode:**  Optimizes the dark color scheme for AMOLED displays by using a true
- *     black background when `isAmoled` is true. This can save battery life on compatible devices.
- * *   **Color Inversion:** Inverts the colors of the generated color scheme with `isInvertColors`
- *     (Note: This feature is automatically disabled when dynamic color is active). Useful for
- *     accessibility and unique design styles.
- * *   **Palette Styles:** Offers various palette styles (e.g., `PaletteStyle.TonalSpot`) to
- *     customize the algorithm used to generate the color scheme. Each colorPaletteStyle produces a
- *     different color harmony and visual feel */
@Composable
fun DynamicTheme(
    seedColor : Color,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    isAmoled: Boolean = false,
    isInvertColors: Boolean = false,
    colorPaletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    colorContrastLevel: Double = 0.00,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = Typography(),
    isAnimateColorScheme: Boolean = true,
    colorTransitionSpec: AnimationSpec<Color> = tween(300),
    content: @Composable () -> Unit,
) {
    val initialColorTuple = rememberColorTuple(
        colorTuple = ColorTuple(primary = seedColor),
        isDynamicColor = isDynamicColor,
        isDarkTheme = isDarkTheme
    )

    val dynamicThemeState = rememberThemeState(initialColorTuple = initialColorTuple)

    val colorScheme = rememberColorScheme(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor ,
        isAmoled = isAmoled,
        isInvertColors = isInvertColors,
        colorTuple = dynamicThemeState.colorTuple.value,
        paletteStyle = colorPaletteStyle,
        contrastLevel = colorContrastLevel
    ).animateAllColors(
        isAnimateColorScheme = isAnimateColorScheme,
        animationSpec = colorTransitionSpec
    )

    LaunchedEffect(initialColorTuple){
        dynamicThemeState.updateColorSeedTuple(initialColorTuple)
    }

    CompositionLocalProvider(LocalDynamicThemeState provides dynamicThemeState) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }

}

/**
 * **DynamicTheme**
 *
 * A dynamic Material Theme that adapts to the provided [ColorTuple], offering extensive customization for color schemes.
 *
 * This composable function allows you to create a Material theme that dynamically changes based on a set of colors,
 * supporting dark and light modes, dynamic color from the system, AMOLED-specific dark themes, color inversion, and more.
 *
 * **Key Features:**
 *
 * *   **Color Customization:**  Utilizes a [ColorTuple] to define the primary color (seed color) and optionally other colors.
 *     The Material color scheme (primary, secondary, tertiary, etc.) is generated based on this seed. For detailed information
 *     on how the colors are derived, refer to the [ColorTuple] documentation.
 * *   **Dynamic Color Support:** Respects the device's dynamic color settings (if supported) via `isDynamicColor`.
 * *   **Dark/Light Mode:** Adapts to dark and light themes using `isDarkTheme` (defaults to the system setting).
 * *   **AMOLED Mode:** Offers a pure black dark theme when `isAmoled` is true, ideal for OLED screens.
 * *   **Color Inversion:** Inverts the color scheme with `isInvertColors` (note: disabled when `isDynamicColor` is true).
 * *   **Palette Style:** Choose from various Material color palette styles via `colorPaletteStyle` (e.g., tonal spot, vibrant).
 * *   **Contrast Level:** Adjust the contrast of the color scheme using `colorContrastLevel`.
 * *   **Animation:** Optionally animate color changes with `isAnimateColorScheme` and customize the animation using `colorTransitionSpec`.
 * * **Local Theme State:** Provides access to the current color tuple using [LocalDynamicThemeState].
 * * **Customizable design:** change the shapes and typography using [Shapes] and [Typography]
 *
 * **Parameters:**
 *
 * @param colorTuple The [ColorTuple] containing the base colors for the theme. The primary color within this tuple acts as the seed color for generating the entire color scheme.
 * @param isDarkTheme `true` to use a dark theme, `false` for a light theme. Defaults to the system's dark mode setting ([isSystemInDarkTheme]).
 * @param isDynamicColor `true` to enable */
@Composable
fun DynamicTheme(
    colorTuple: ColorTuple,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    isAmoled: Boolean = false,
    isInvertColors: Boolean = false,
    colorPaletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    colorContrastLevel: Double = 0.00,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = Typography(),
    isAnimateColorScheme: Boolean = true,
    colorTransitionSpec: AnimationSpec<Color> = tween(300),
    content: @Composable () -> Unit,
) {
   val defaultColorTuple = rememberColorTuple(
        colorTuple = colorTuple,
        isDynamicColor = isDynamicColor,
        isDarkTheme = isDarkTheme
    )

    val themeState = rememberThemeState(initialColorTuple = defaultColorTuple )

    val scheme = rememberColorScheme(
        isDarkTheme = isDarkTheme,
        isDynamicColor = isDynamicColor ,
        isAmoled = isAmoled,
        isInvertColors = isInvertColors,
        colorTuple = themeState.colorTuple.value,
        paletteStyle = colorPaletteStyle,
        contrastLevel = colorContrastLevel
    ).animateAllColors(
        isAnimateColorScheme = isAnimateColorScheme,
        animationSpec = colorTransitionSpec
    )

    LaunchedEffect(defaultColorTuple){
        themeState.updateColorSeedTuple(defaultColorTuple)
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





