package io.github.tarifchakder.materializekmp.remember

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.tarifchakder.materializekmp.ColorTuple
import io.github.tarifchakder.materializekmp.getColorScheme
import io.github.tarifchakder.materializekmp.getObserveAsState

/**
- * Creates and remember [ColorTuple] instance to based on the three main colors
+ * Creates and remembers a [ColorTuple] instance.
+ *
+ * This function manages the creation and caching of a [ColorTuple], dynamically
+ * switching between a predefined `colorTuple` and a dynamically generated
+ * color scheme based on the `isDynamicColor` and `isDarkTheme` parameters.
+ *
+ * @param colorTuple The default [ColorTuple] to use if dynamic color generation is not enabled or available.
+ * @param isDynamicColor `true` if dynamic color generation should be attempted, `false` otherwise.
+ * @param isDarkTheme `true` if the generated color scheme should be for dark mode, `false` for light mode. Only relevant when `isDynamicColor` is `true`.
+ * @return A [ColorTuple] instance, either the provided `colorTuple` or a dynamically generated one.
+ *
+ * The function uses `remember` to cache the result based on changes in `getObserveAsState()`,
+ * `colorTuple`, `isDynamicColor`, and `isDarkTheme`. If `isDynamicColor` is `true` and a color scheme
+ * is available it will return a new ColorTuple based on it, otherwise it will return the given colorTuple.
 * */
@Composable
fun rememberColorTuple(
    colorTuple: ColorTuple,
    isDynamicColor: Boolean,
    isDarkTheme: Boolean
): ColorTuple {
    var onTrigger by remember { mutableStateOf(false) }

    var colorScheme : ColorScheme? = getColorScheme(isDarkTheme)

    if (onTrigger) colorScheme =  getColorScheme(isDarkTheme)

    return remember(
        getObserveAsState(),
        colorTuple,
        isDynamicColor,
        isDarkTheme
    ) {
        onTrigger = true

        when {
            isDynamicColor && colorScheme != null -> {
                ColorTuple(
                    colorScheme.primary,
                    colorScheme.secondary,
                    colorScheme.tertiary,
                    colorScheme.surface
                )
            }

            else -> colorTuple
        }
    }
}