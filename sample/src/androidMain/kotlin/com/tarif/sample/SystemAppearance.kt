package com.tarif.sample

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SystemAppearance(isDark: Boolean) {
    val view = LocalView.current
    val systemBarColor = MaterialTheme.colorScheme.surface.toArgb()
    LaunchedEffect(!isDark) {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = systemBarColor
        window.navigationBarColor = systemBarColor
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isDark
            isAppearanceLightNavigationBars = !isDark
        }
    }
}