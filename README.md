# KMP Material Theme

It is a Compose Multiplatform library targeting Android, iOS, Web, Desktop and designed to provide a Material Design 3 based theme, along with essential components for easy configuration

## Support Platforms

This library is written for Compose Multiplatform, and can be used on the following platforms:

- Android
- iOS
- JVM (Desktop)
- JavaScript/wasm (Browser)

## Configuration

1. Paste this code to your _.toml_ file
```toml
[versions]

ktheme = "0.0.3"

[libraries]

ktheme = { module = "com.feraxhp.ktheme:ktheme-compose", version.ref = "ktheme" }
```
2. Then paste this other code into your _build.gradle.kts_ in `kotlin > sourceSets > commonMain.dependencies`
```kotlin
implementation(libs.ktheme)
```
3. Sync Project with Gradle
4. Now you can add DynamicTheme { } anywhere in your project, here is an example on your _App.kt_
```kotlin
import com.feraxhp.ktheme.DynamicTheme

@Composable
internal fun App() = DynamicTheme { // base color default is: 0X4C662B, to change it call baseColor: Int 
  // Your KMP App content
}
```
5. Update settings
```kotlin
val dts = LocalThemeSettings.currentOrThrow // Provider

// Access Settings

val seedColor by dts.seedColor.collectAsState()
val secondary by dts.secondary.collectAsState()
val tertiary by dts.tertiary.collectAsState()
val error by dts.error.collectAsState()
val theme by dts.theme.collectAsState()
val useDynamicColor by dts.useDynamicColor.collectAsState()
val useAmoled by dts.useAmoled.collectAsState()
val style by dts.style.collectAsState()
val contrastLevel by dts.contrastLevel.collectAsState()
val extendedFidelity by dts.extendedFidelity.collectAsState()
val hasAnimation by dts.hasAnimation.collectAsState()

// LocalThemeSettings: Provides set-functions to change every theme Setting.
```

