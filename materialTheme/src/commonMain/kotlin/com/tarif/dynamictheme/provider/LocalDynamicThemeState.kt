package com.tarif.dynamictheme.provider

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.tarif.dynamictheme.DynamicThemeState

val LocalDynamicThemeState: ProvidableCompositionLocal<DynamicThemeState> = compositionLocalOf { error("DynamicThemeState not present") }
