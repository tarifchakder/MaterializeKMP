package com.tarif.dynamictheme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle

@Composable
internal expect fun getColorScheme(isDarkTheme: Boolean): ColorScheme?

@Composable
internal expect fun getObserveAsState(): Lifecycle.Event?