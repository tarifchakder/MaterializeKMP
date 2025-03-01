package com.tarif.dynamictheme

/**
- * [ThemeMode] to set current theme mode of the application
+ * Represents the available theme modes for the application.
+ * This enum allows you to specify whether the app should use the system's theme, a light theme, or a dark theme.
 */
enum class ThemeMode(val stringValue: String) {
    SYSTEM("SYSTEM"),
    LIGHT("LIGHT"),
    DARK("DARK");

    companion object {
        fun fromString(value: String): ThemeMode {
            return entries.firstOrNull { it.stringValue.equals(value, ignoreCase = true) } ?: SYSTEM
        }
    }
}