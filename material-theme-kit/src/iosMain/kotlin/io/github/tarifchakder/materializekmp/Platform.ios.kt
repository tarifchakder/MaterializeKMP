package io.github.tarifchakder.materializekmp

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle

@Composable
actual fun getColorScheme(isDarkTheme: Boolean): ColorScheme? {
    return null
}

@Composable
actual fun getObserveAsState(): Lifecycle.Event? {
    return null
}

actual fun isAndroidPlatform(): Boolean = false