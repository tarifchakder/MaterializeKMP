package com.tarif.sample.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tarif.sample.component.SegmentedButtonsDefaults.ITEM_ANIMATION_MILLIS
import com.tarif.sample.component.SegmentedButtonsDefaults.minimumHeight
import com.tarif.sample.component.SegmentedButtonsDefaults.outlineThickness

/**
 * Segmented buttons implemented similar to M3 spec. Use for simple choices between two to five items.
 * Each button contains a label and an icon.
 *
 * @param modifier The modifier to be applied to these SegmentedButtons.
 * @param shape The shape of the SegmentedButtons.
 * @param colors The colors to style these SegmentedButtons.
 * @param outlineThickness The thickness of the outline and divider for these Segmented Buttons.
 * @param border The border stroke for the outline of these Segmented Buttons.
 * @param content The content of the SegmentedButtons, usually 3-5 [SegmentedButtonItem].
 */
@Composable
fun SegmentedButtons(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(percent = 50),
    colors: SegmentedButtonColors = SegmentedButtonsDefaults.colors(),
    outlineThickness: Dp = SegmentedButtonsDefaults.outlineThickness,
    border: BorderStroke = BorderStroke(outlineThickness, colors.outlineColor),
    content: @Composable () -> Unit
) {
    Surface(
        shape = shape,
        border = border,
        modifier = modifier.defaultMinSize(minHeight = minimumHeight)
    ) {
        SubcomposeLayout(Modifier.fillMaxWidth()) { constraints ->
            val bottonRowWidth = constraints.maxWidth
            val buttonMeasurables = subcompose(ButtonSlots.Buttons, content)
            val buttonCount = buttonMeasurables.size
            val dividerCount = buttonMeasurables.size - 1

            val outlineThicknessPx = outlineThickness.roundToPx()

            var buttonWidth = 0
            if (buttonCount > 0) {
                buttonWidth = (bottonRowWidth / buttonCount)
            }
            val buttonRowHeight =
                buttonMeasurables.fold(initial = minimumHeight.roundToPx()) { max, curr ->
                    maxOf(curr.maxIntrinsicHeight(buttonWidth), max)
                }

            val buttonPlaceables = buttonMeasurables.map {
                it.measure(
                    constraints.copy(
                        minWidth = buttonWidth,
                        maxWidth = buttonWidth,
                        minHeight = buttonRowHeight,
                        maxHeight = buttonRowHeight,
                    )
                )
            }
            val dividers = @Composable {
                repeat(dividerCount) {
                    colors.SegmentedDivider()
                }
            }
            val dividerPlaceables =
                subcompose(ButtonSlots.Divider, dividers).map {
                    it.measure(
                        constraints.copy(
                            minWidth = outlineThicknessPx,
                            maxWidth = outlineThicknessPx,
                            minHeight = buttonRowHeight - outlineThicknessPx * 2,
                            maxHeight = buttonRowHeight - outlineThicknessPx * 2,
                        )
                    )
                }

            layout(bottonRowWidth, buttonRowHeight) {
                buttonPlaceables.forEachIndexed { index, button ->
                    if (index < dividerPlaceables.size) {
                        dividerPlaceables[index].placeRelative(
                            index * buttonWidth + buttonWidth,
                            outlineThicknessPx,
                            1f
                        )
                    }
                    button.placeRelative(index * buttonWidth, 0, 0f)
                }
            }
        }
    }
}

/**
 * Material Design Segmented Button item. Use for simple choices between two to five items.
 *
 * @param selected Whether this item is selected.
 * @param onClick Called when this item is clicked.
 * @param modifier The modifier to apply to this item.
 * @param label Optional label for this item.
 * @param icon Optional icon for this item.
 * @param colors Colors to style this item.
 * @param textStyle Text style to be applied to the label of this item.
 */
@Composable
fun SegmentedButtonItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    colors: SegmentedButtonColors = SegmentedButtonsDefaults.colors(),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    val styledLabel: @Composable (() -> Unit)? =
        label?.let {
            @Composable {
                val textColor by colors.textColor(selected = selected)
                CompositionLocalProvider(LocalContentColor provides textColor) {
                    ProvideTextStyle(textStyle, content = label)
                }
            }
        }

    val styledIcon: @Composable (() -> Unit)? =
        icon?.let {
            @Composable {
                val iconColor by colors.iconColor(selected = selected)
                val clearSemantics = label != null && selected
                Box(modifier = if (clearSemantics) Modifier.clearAndSetSemantics {} else Modifier) {
                    CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
                }
            }
        }

    val animationProgress: Float by animateFloatAsState(
        targetValue = if (selected) colors.indicatorColor.alpha else 0f,
        animationSpec = tween(ITEM_ANIMATION_MILLIS), label = "SegmentedButton"
    )

    Box(
        modifier
            .fillMaxSize()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Tab,
            )
            .background(
                color = colors.indicatorColor.copy(alpha = animationProgress),
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            styledIcon?.invoke()
            styledLabel?.invoke()
        }
    }
}

object SegmentedButtonsDefaults {

    @Composable
    fun colors(
        selectedTextColor: Color = MaterialTheme.colorScheme.primary,
        selectedIconColor: Color = MaterialTheme.colorScheme.primary,
        unselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        unselectedIconColor: Color = MaterialTheme.colorScheme.onSurface,
        indicatorColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        outlineColor: Color = MaterialTheme.colorScheme.outlineVariant
    ): SegmentedButtonColors = SegmentedButtonColors(
        selectedTextColor = selectedTextColor,
        selectedIconColor = selectedIconColor,
        indicatorColor = indicatorColor,
        unselectedTextColor = unselectedTextColor,
        unselectedIconColor = unselectedIconColor,
        outlineColor = outlineColor
    )

    internal val outlineThickness: Dp = 1.dp
    internal val minimumHeight: Dp = 48.dp
    internal const val ITEM_ANIMATION_MILLIS: Int = 100
}

@Stable
data class SegmentedButtonColors internal constructor(
    val selectedTextColor: Color,
    val selectedIconColor: Color,
    val unselectedTextColor: Color,
    val unselectedIconColor: Color,
    val indicatorColor: Color,
    val outlineColor: Color
) {
    @Composable
    internal fun textColor(selected: Boolean): State<Color> {
        val targetValue = when {
            selected -> selectedTextColor
            else -> unselectedTextColor
        }
        return animateColorAsState(
            targetValue = targetValue,
            animationSpec = tween(ITEM_ANIMATION_MILLIS),
            label = "SegmentedButtonsTextColor"
        )
    }

    @Composable
    fun iconColor(selected: Boolean): State<Color> {
        val targetValue = when {
            selected -> selectedIconColor
            else -> unselectedIconColor
        }
        return animateColorAsState(
            targetValue = targetValue,
            animationSpec = tween(ITEM_ANIMATION_MILLIS),
            label = "SegmentedButtonsIconColor"
        )
    }

    @Composable
    fun SegmentedDivider(
        modifier: Modifier = Modifier,
        thickness: Dp = outlineThickness,
        color: Color = outlineColor
    ) {
        Box(
            modifier
                .fillMaxHeight()
                .width(thickness)
                .background(color = color)
        )
    }
}

private enum class ButtonSlots {
    Buttons,
    Divider,
}