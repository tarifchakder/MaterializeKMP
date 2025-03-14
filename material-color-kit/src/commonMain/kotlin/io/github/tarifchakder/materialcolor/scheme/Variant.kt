package io.github.tarifchakder.materialcolor.scheme

/**
 * Themes for Dynamic Color.
 */
enum class Variant {

    /**
     * A monochrome theme, colors are purely black / white / gray.
     */
    MONOCHROME,

    /**
     * A theme that's slightly more chromatic than monochrome, which is purely black / white / gray.
     */
    NEUTRAL,

    /**
     * A calm theme, sedated colors that aren't particularly chromatic.
     */
    TONAL_SPOT,

    /**
     * A loud theme, colorfulness is maximum for Primary palette, increased for others.
     */
    VIBRANT,

    /**
     * A playful theme - the source color's hue does not appear in the theme.
     */
    EXPRESSIVE,

    /**
     * A scheme that places the source color in Scheme.primaryContainer.
     *
     * Primary Container is the source color, adjusted for color relativity. It maintains constant
     * appearance in light mode and dark mode. This adds ~5 tone in light mode, and subtracts ~5 tone in
     * dark mode.
     *
     * Tertiary Container is the complement to the source color, using TemperatureCache. It also
     * maintains constant appearance.
     */
    FIDELITY,

    /**
     * A scheme that places the source color in Scheme.primaryContainer.
     *
     * Primary Container is the source color, adjusted for color relativity. It maintains constant
     * appearance in light mode and dark mode. This adds ~5 tone in light mode, and subtracts ~5 tone in
     * dark mode.
     *
     * Tertiary Container is an analogous color, specifically, the analog of a color wheel divided
     * into 6, and the precise analog is the one found by increasing hue. This is a scientifically
     * grounded equivalent to rotating hue clockwise by 60 degrees. It also maintains constant
     * appearance.
     */
    CONTENT,

    /**
     * A playful theme - the source color's hue does not appear in the theme.
     */
    RAINBOW,

    /**
     * A playful theme - the source color's hue does not appear in the theme.
     */
    FRUIT_SALAD,
}
