package com.tarif.dynamictheme.extension

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.tarif.dynamictheme.ThemeMode

/**
 * Using this extension to check current
 * [ThemeMode] is in Dark theme or not
 * */
@Composable
fun ThemeMode.isDarkTheme(): Boolean {
    return when (this) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
}