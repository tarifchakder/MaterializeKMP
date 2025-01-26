package com.tarif.dynamictheme.provider

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.tarif.dynamictheme.DynamicThemeState

val LocalDynamicThemeState: ProvidableCompositionLocal<DynamicThemeState?> = staticCompositionLocalOf { error("DynamicThemeState not present") }
