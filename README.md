# 🌗 KMP Material Theme (MaterializeKMP)

[![Maven Central](https://img.shields.io/maven-central/v/com.tarifchakder/material-theme)](https://central.sonatype.com/artifact/com.tarifchakder/material-theme)
[![License](https://img.shields.io/github/license/tarifchakder/MaterializeKMP)](LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.0-blue)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](https://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](https://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](https://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](https://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)

---

## ✨ Overview

MaterializeKMP is a Kotlin Multiplatform library for Compose Multiplatform that provides a Material Design 3–based theme system and essentials to keep your UI consistent across platforms.

Targets supported:
- Android
- iOS
- Desktop (JVM)
- Web (Wasm/JS)

The repository also contains a multiplatform Sample app demonstrating usage on all targets, plus an iOS host project.

---

## 🖥️ Demo
|              Android               |               Web               |            iOS             |
|:----------------------------------:|:-------------------------------:|:--------------------------:|
| ![Android](screenshot/android.gif) | ![Web](screenshot/web_demo.gif) | ![iOS](screenshot/ios.gif) |

---

## 🧰 Tech stack
- Language: Kotlin (2.0.20)
- UI: JetBrains Compose Multiplatform (1.7.0)
- Build: Gradle (Kotlin DSL) with Version Catalog
- Android Gradle Plugin: 8.5.2
- JDK: 17
- Modules:
  - material-theme-kit (library, published as com.tarifchakder:material-theme)
  - sample (KMP demo app: android, desktop, wasmJs, iOS)
  - iosApp (Xcode project to run the sample on iOS)

---

## 🔧 Requirements
- JDK 17
- Android Studio (to run Android/Desktop and manage Gradle)
- Xcode (to run the iOS host project)
- Android SDK: compileSdk 36, minSdk 24, targetSdk 36
- macOS is required to build/run iOS targets

---

## 📦 Installation (use in your project)
Add the dependency to your commonMain source set:

```kotlin
commonMain {
    dependencies {
        implementation("com.tarifchakder:material-theme:1.1.0")
    }
}
```

Using a Version Catalog:

```toml
[versions]
materialtheme = "1.1.0"

[libraries]
material-theme = { module = "com.tarifchakder:material-theme", version.ref = "materialtheme" }
```

---

## ▶️ Running the sample
Repository uses the Gradle Wrapper. Replace ./gradlew with gradlew.bat on Windows.

- Desktop (JVM):
  - Run: `./gradlew :sample:run`
  - Distributable: `./gradlew :sample:createDistributable`

- Android:
  - Assemble: `./gradlew :sample:assembleDebug`
  - Install on device/emulator: `./gradlew :sample:installDebug`
  - Recommended: Open the project in Android Studio and run the sample configuration.

- Web (Wasm/JS):
  - Dev server (auto-reload): `./gradlew :sample:wasmJsBrowserDevelopmentRun`
  - Production build: `./gradlew :sample:wasmJsBrowserDistribution`

- iOS:
  - Open the Xcode project: `iosApp/iosApp.xcodeproj`
  - Select the iosApp scheme and a simulator/device, then Run.
  - The iOS host imports the KMP sample via `ComposeUIViewController` (see iosApp/iosApp/ContentView.swift).

Note: For iOS, Gradle can build KMP frameworks for targets (iosX64/iosArm64/iosSimulatorArm64). The included Xcode project has been wired to the shared `sample` code via the generated framework; if something fails, open Xcode and let it resolve Swift packages and build steps.

---

## 📜 Scripts and useful Gradle tasks
- Build everything: `./gradlew build`
- Clean: `./gradlew clean`
- Lint (Android): handled by AGP; see module gradle files
- Documentation (Dokka): `./gradlew :material-theme-kit:dokkaHtml`
- Publish to Maven Central (library):
  - Local: `./gradlew :material-theme-kit:publishToMavenLocal`
  - Remote (CI): `./gradlew publishAllPublicationsToMavenCentralRepository`
    - See `.github/workflows/release.yml` – the workflow is triggered on version bump in `version.properties`.

---

## 🧪 Tests
- The library configures `commonTest` with Kotlin test dependencies, but no tests are currently present.
- TODO: Add unit tests for color generation and theming behavior.

Run all tests:
- `./gradlew test`

---

## 🗂 Project structure
- material-theme-kit
  - Kotlin Multiplatform library (Android, iOS, Desktop, Wasm/JS)
  - Gradle publish and Dokka configured
- sample
  - Multiplatform demo app showing library usage
  - Entry points:
    - Android: `sample/src/androidMain/.../MainActivity.kt`
    - Desktop: `sample/src/desktopMain/.../main.kt` (main class: `com.tarif.sample.MainKt`)
    - Web/Wasm: `sample/src/wasmJsMain/.../main.kt`
    - iOS: `sample/src/iosMain/.../MainViewController.kt`
- iosApp
  - Native Xcode project hosting the KMP UI (see `iosApp/iosApp/ContentView.swift`)

Root settings: see `settings.gradle.kts`, versions in `gradle/libs.versions.toml` and `version.properties`.

---

## 📦 Features
- Material Design 3 theming
- Light/Dark themes
- Dynamic color palette support
- Works across Android, iOS, Desktop, Web/Wasm
- Easy integration via Gradle Version Catalogs

---

## 📖 Usage
See the sample for a complete setup. Core entry point for theming is `DynamicTheme` in the library. Example (simplified):

```kotlin
DynamicTheme(
    seedColor = Color(0xFF00BCD4),
    isDarkTheme = false,
    isDynamicColor = true,
    isAmoled = false,
    isInvertColors = false
) {
    // Your Compose UI here
}
```

---

## 🧾 License
MaterializeKMP is licensed under the MIT License.

This library draws inspiration from the Material Color Palette by the Material Foundation. For details on their licensing, see their LICENSE file:
- material-theme-kit/src/commonMain/kotlin/io/github/tarifchakder/materializekmp/materialcolor/LICENSE

See [LICENSE](LICENSE) for the repository license.

---

## 🤝 Contributing
Contributions are welcome! If you have an idea for a new feature or an enhancement:
1. Fork the repository.
2. Create a branch from `develop`: `git checkout -b feature/my-new-feature`.
3. Open a pull request targeting `develop` and describe your changes.

Please consider adding tests and updating documentation for user-facing changes.



