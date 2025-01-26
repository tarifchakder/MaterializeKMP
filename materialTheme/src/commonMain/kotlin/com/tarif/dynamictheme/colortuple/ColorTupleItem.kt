package com.tarif.dynamictheme.colortuple

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.tarif.kmp.materialcolor.hct.Hct
import com.tarif.kmp.materialcolor.palettes.TonalPalette

/**
 * Composable representing ColorTuple object
 * **/
@Composable
fun ColorTupleItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    colorTuple: ColorTuple,
    content: (@Composable BoxScope.() -> Unit)? = null
) {
    val (primary, secondary, tertiary) = remember(colorTuple) {
        derivedStateOf {
            colorTuple.run {
                val hct = Hct.fromInt(colorTuple.primary.toArgb())
                val hue = hct.hue
                val chroma = hct.chroma

                val secondary = colorTuple.secondary?.toArgb().let {
                    if (it != null) {
                        TonalPalette.fromInt(it)
                    } else {
                        TonalPalette.fromHueAndChroma(hue, chroma / 3.0)
                    }
                }
                val tertiary = colorTuple.tertiary?.toArgb().let {
                    if (it != null) {
                        TonalPalette.fromInt(it)
                    } else {
                        TonalPalette.fromHueAndChroma(hue + 60.0, chroma / 2.0)
                    }
                }

                Triple(
                    primary,
                    colorTuple.secondary ?: Color(secondary.tone(70)),
                    colorTuple.tertiary ?: Color(tertiary.tone(70))
                )
            }
        }
    }.value.run {
        Triple(
            animateColorAsState(targetValue = first, label = "").value,
            animateColorAsState(targetValue = second, label = "").value,
            animateColorAsState(targetValue = third, label = "").value
        )
    }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(primary)
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(tertiary)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(secondary)
                    )
                }
            }
            content?.invoke(this)
        }
    }
}