package io.github.tarifchakder.materializekmp

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.github.tarifchakder.materializekmp.extension.observeAsState

@Composable
actual fun getColorScheme(isDarkTheme: Boolean): ColorScheme? {
    val context = LocalContext.current
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (isDarkTheme)
            dynamicDarkColorScheme(context)
        else
            dynamicLightColorScheme(context)
    } else {
        null
    }
}

@Composable
actual fun getObserveAsState(): Lifecycle.Event? {
    return LocalLifecycleOwner.current.lifecycle.observeAsState().value
}

actual fun isAndroidPlatform(): Boolean = true