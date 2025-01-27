package com.tarif.dynamictheme.extension

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.tarif.dynamictheme.ColorTuple
import com.tarif.dynamictheme.PaletteStyle
import com.tarif.kmp.materialcolor.dynamiccolor.MaterialDynamicColors
import com.tarif.kmp.materialcolor.hct.Hct
import com.tarif.kmp.materialcolor.palettes.TonalPalette
import com.tarif.kmp.materialcolor.scheme.DynamicScheme
import com.tarif.kmp.materialcolor.scheme.SchemeContent
import com.tarif.kmp.materialcolor.scheme.SchemeExpressive
import com.tarif.kmp.materialcolor.scheme.SchemeFidelity
import com.tarif.kmp.materialcolor.scheme.SchemeFruitSalad
import com.tarif.kmp.materialcolor.scheme.SchemeMonochrome
import com.tarif.kmp.materialcolor.scheme.SchemeNeutral
import com.tarif.kmp.materialcolor.scheme.SchemeRainbow
import com.tarif.kmp.materialcolor.scheme.SchemeTonalSpot
import com.tarif.kmp.materialcolor.scheme.SchemeVibrant
import com.tarif.kmp.materialcolor.scheme.Variant

/**
 * Generate a [ColorScheme] based on the given [isAmoled].
 *
 * @param[isAmoled] Whether the scheme should be dark or light.
 * @return The generated [ColorScheme].
 */
fun ColorScheme.toAmoled(
    isAmoled: Boolean
): ColorScheme {

    fun Color.darken(fraction: Float = 0.5f): Color {
        return this.blend(Color.Black, fraction)
    }

    return if (isAmoled) {
        copy(
            primary = primary.darken(0.3f),
            onPrimary = onPrimary.darken(0.3f),
            primaryContainer = primaryContainer.darken(0.3f),
            onPrimaryContainer = onPrimaryContainer.darken(0.3f),
            inversePrimary = inversePrimary.darken(0.3f),
            secondary = secondary.darken(0.3f),
            onSecondary = onSecondary.darken(0.3f),
            secondaryContainer = secondaryContainer.darken(0.3f),
            onSecondaryContainer = onSecondaryContainer.darken(0.3f),
            tertiary = tertiary.darken(0.3f),
            onTertiary = onTertiary.darken(0.3f),
            tertiaryContainer = tertiaryContainer.darken(0.3f),
            onTertiaryContainer = onTertiaryContainer.darken(0.2f),
            background = Color.Black,
            onBackground = onBackground.darken(0.15f),
            surface = Color.Black,
            onSurface = onSurface.darken(0.15f),
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceTint = surfaceTint,
            inverseSurface = inverseSurface.darken(),
            inverseOnSurface = inverseOnSurface.darken(0.2f),
            outline = outline.darken(0.2f),
            outlineVariant = outlineVariant.darken(0.2f)
        )
    } else this

}

/**
 * Create [ColorScheme] based on the given [DynamicScheme].
 */
fun DynamicScheme.toColorScheme(): ColorScheme {
    val colors = MaterialDynamicColors()
    val scheme = this
    return ColorScheme(
        primary = Color(colors.primary().getArgb(scheme)),
        onPrimary = Color(colors.onPrimary().getArgb(scheme)),
        primaryContainer = Color(colors.primaryContainer().getArgb(scheme)),
        onPrimaryContainer = Color(colors.onPrimaryContainer().getArgb(scheme)),
        inversePrimary = Color(colors.inversePrimary().getArgb(scheme)),
        secondary = Color(colors.secondary().getArgb(scheme)),
        onSecondary = Color(colors.onSecondary().getArgb(scheme)),
        secondaryContainer = Color(colors.secondaryContainer().getArgb(scheme)),
        onSecondaryContainer = Color(colors.onSecondaryContainer().getArgb(scheme)),
        tertiary = Color(colors.tertiary().getArgb(scheme)),
        onTertiary = Color(colors.onTertiary().getArgb(scheme)),
        tertiaryContainer = Color(colors.tertiaryContainer().getArgb(scheme)),
        onTertiaryContainer = Color(colors.onTertiaryContainer().getArgb(scheme)),
        background = Color(colors.background().getArgb(scheme)),
        onBackground = Color(colors.onBackground().getArgb(scheme)),
        surface = Color(colors.surface().getArgb(scheme)),
        onSurface = Color(colors.onSurface().getArgb(scheme)),
        surfaceVariant = Color(colors.surfaceVariant().getArgb(scheme)),
        onSurfaceVariant = Color(colors.onSurfaceVariant().getArgb(scheme)),
        surfaceTint = Color(colors.surfaceTint().getArgb(scheme)),
        inverseSurface = Color(colors.inverseSurface().getArgb(scheme)),
        inverseOnSurface = Color(colors.inverseOnSurface().getArgb(scheme)),
        error = Color(colors.error().getArgb(scheme)),
        onError = Color(colors.onError().getArgb(scheme)),
        errorContainer = Color(colors.errorContainer().getArgb(scheme)),
        onErrorContainer = Color(colors.onErrorContainer().getArgb(scheme)),
        outline = Color(colors.outline().getArgb(scheme)),
        outlineVariant = Color(colors.outlineVariant().getArgb(scheme)),
        scrim = Color(colors.scrim().getArgb(scheme)),
        surfaceBright = Color(colors.surfaceBright().getArgb(scheme)),
        surfaceDim = Color(colors.surfaceDim().getArgb(scheme)),
        surfaceContainer = Color(colors.surfaceContainer().getArgb(scheme)),
        surfaceContainerHigh = Color(colors.surfaceContainerHigh().getArgb(scheme)),
        surfaceContainerHighest = Color(colors.surfaceContainerHighest().getArgb(scheme)),
        surfaceContainerLow = Color(colors.surfaceContainerLow().getArgb(scheme)),
        surfaceContainerLowest = Color(colors.surfaceContainerLowest().getArgb(scheme)),
    )
}

/**
 * Create invert [ColorScheme] if dynamic color disabled .
 * @param [enabled] whether invert color enabled
 * @return The generated [ColorScheme]
 */
fun ColorScheme.invertColors(
    enabled: Boolean
): ColorScheme {

    fun Color.invertColor(): Color {
        return if (enabled) {
            Color(this.toArgb() xor 0x00ffffff)
        } else this
    }

    return this.copy(
        primary = primary.invertColor(),
        onPrimary = onPrimary.invertColor(),
        primaryContainer = primaryContainer.invertColor(),
        onPrimaryContainer = onPrimaryContainer.invertColor(),
        inversePrimary = inversePrimary.invertColor(),
        secondary = secondary.invertColor(),
        onSecondary = onSecondary.invertColor(),
        secondaryContainer = secondaryContainer.invertColor(),
        onSecondaryContainer = onSecondaryContainer.invertColor(),
        tertiary = tertiary.invertColor(),
        onTertiary = onTertiary.invertColor(),
        tertiaryContainer = tertiaryContainer.invertColor(),
        onTertiaryContainer = onTertiaryContainer.invertColor(),
        background = background.invertColor(),
        onBackground = onBackground.invertColor(),
        surface = surface.invertColor(),
        onSurface = onSurface.invertColor(),
        surfaceVariant = surfaceVariant.invertColor(),
        onSurfaceVariant = onSurfaceVariant.invertColor(),
        surfaceTint = surfaceTint.invertColor(),
        inverseSurface = inverseSurface.invertColor(),
        inverseOnSurface = inverseOnSurface.invertColor(),
        error = error.invertColor(),
        onError = onError.invertColor(),
        errorContainer = errorContainer.invertColor(),
        onErrorContainer = onErrorContainer.invertColor(),
        outline = outline.invertColor(),
        outlineVariant = outlineVariant.invertColor()
    )
}

/**
 * Generate a [DynamicScheme] based on the given [ColorTuple].
 *
 * @param[isDarkTheme] Whether the scheme should be dark or light.
 * @param[isDynamicColor] Whether the dynamic color should be enabled.
 * @return The generated [DynamicScheme].
 */
fun ColorTuple.toDynamicScheme(
    isDynamicColor: Boolean,
    isDarkTheme: Boolean,
    style: PaletteStyle,
    contrastLevel: Double
): DynamicScheme {
    val hct = Hct.fromInt(this.primary.toArgb())
    val hue = hct.hue
    val chroma = hct.chroma

    val primaryPalette = this.primary.toArgb().let {
        TonalPalette.fromInt(it)
    }

    val secondaryPalette = this.secondary?.toArgb().let { color ->
        if (color != null) {
            TonalPalette.fromInt(color)
        } else {
            TonalPalette.fromHueAndChroma(hue, chroma / 3.0)
        }
    }

    val tertiaryPalette = this.tertiary?.toArgb().let { color ->
        if (color != null) {
            TonalPalette.fromInt(color)
        } else {
            TonalPalette.fromHueAndChroma(hue + 60.0, chroma / 2.0)
        }
    }

    val neutralPalette = this.surface?.toArgb().let { color ->
        if (color != null) {
            TonalPalette.fromInt(color)
        } else {
            TonalPalette.fromHueAndChroma(hue, (chroma / 12.0).coerceAtMost(4.0))
        }
    }

    val neutralVariantPalette = TonalPalette.fromInt(neutralPalette.tone(90))

    val dynamicScheme = if (isDynamicColor) {
        DynamicScheme(
            hct,
            Variant.TONAL_SPOT,
            isDarkTheme,
            0.0,
            primaryPalette,
            secondaryPalette,
            tertiaryPalette,
            neutralPalette,
            neutralVariantPalette
        )
    } else {
        this.primary.toDynamicScheme(isDarkTheme, style, contrastLevel)
    }

    return dynamicScheme
}

/**
 * Generate a [DynamicScheme] based on the given [Color].
 *
 * @param[isDarkTheme] Whether the scheme should be dark or light.
 * @param[style] The style of the scheme, see [PaletteStyle].
 * @param[contrastLevel] The contrast level of the scheme.
 * @return The generated [DynamicScheme].
 */
fun Color.toDynamicScheme(
    isDarkTheme: Boolean,
    style: PaletteStyle,
    contrastLevel: Double = 0.00,
): DynamicScheme {
    val hct = Hct.fromInt(this.toArgb())
    return when (style) {
        PaletteStyle.TonalSpot -> SchemeTonalSpot(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Neutral -> SchemeNeutral(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Vibrant -> SchemeVibrant(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Expressive -> SchemeExpressive(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Rainbow -> SchemeRainbow(hct, isDarkTheme, contrastLevel)
        PaletteStyle.FruitSalad -> SchemeFruitSalad(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Monochrome -> SchemeMonochrome(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Fidelity -> SchemeFidelity(hct, isDarkTheme, contrastLevel)
        PaletteStyle.Content -> SchemeContent(hct, isDarkTheme, contrastLevel)
    }
}

/**
 * This function animates colors when current color scheme changes.
 *
 * @param[isAnimateColorScheme] Whether to animate the color scheme or not.
 * @param animationSpec Animation that will be applied when theming option changes.
 * @return [ColorScheme] with animated colors.
 */
@Composable
fun ColorScheme.animateAllColors(
    isAnimateColorScheme: Boolean,
    animationSpec: AnimationSpec<Color>
): ColorScheme {

    /**
     * Wraps color into [animateColorAsState].
     *
     * @return Animated [Color].
     */
    @Composable
    fun Color.animateColor() = animateColorAsState(this, animationSpec, label = "Animate colorScheme").value

    return if (isAnimateColorScheme) {
        this.copy(
            primary = primary.animateColor(),
            onPrimary = onPrimary.animateColor(),
            primaryContainer = primaryContainer.animateColor(),
            onPrimaryContainer = onPrimaryContainer.animateColor(),
            inversePrimary = inversePrimary.animateColor(),
            secondary = secondary.animateColor(),
            onSecondary = onSecondary.animateColor(),
            secondaryContainer = secondaryContainer.animateColor(),
            onSecondaryContainer = onSecondaryContainer.animateColor(),
            tertiary = tertiary.animateColor(),
            onTertiary = onTertiary.animateColor(),
            tertiaryContainer = tertiaryContainer.animateColor(),
            onTertiaryContainer = onTertiaryContainer.animateColor(),
            background = background.animateColor(),
            onBackground = onBackground.animateColor(),
            surface = surface.animateColor(),
            onSurface = onSurface.animateColor(),
            surfaceVariant = surfaceVariant.animateColor(),
            onSurfaceVariant = onSurfaceVariant.animateColor(),
            surfaceTint = surfaceTint.animateColor(),
            inverseSurface = inverseSurface.animateColor(),
            inverseOnSurface = inverseOnSurface.animateColor(),
            error = error.animateColor(),
            onError = onError.animateColor(),
            errorContainer = errorContainer.animateColor(),
            onErrorContainer = onErrorContainer.animateColor(),
            outline = outline.animateColor(),
            outlineVariant = outlineVariant.animateColor(),
            scrim = scrim.animateColor()
        )
    } else {
        this.copy(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            inversePrimary = inversePrimary,
            secondary = secondary,
            onSecondary = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
            tertiary = tertiary,
            onTertiary = onTertiary,
            tertiaryContainer = tertiaryContainer,
            onTertiaryContainer = onTertiaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            surfaceTint = surfaceTint,
            inverseSurface = inverseSurface,
            inverseOnSurface = inverseOnSurface,
            error = error,
            onError = onError,
            errorContainer = errorContainer,
            onErrorContainer = onErrorContainer,
            outline = outline,
            outlineVariant = outlineVariant,
            scrim = scrim
        )
    }
}
