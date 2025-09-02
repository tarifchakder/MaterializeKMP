# 🌗 KMP Material Theme

[![Maven Central](https://img.shields.io/maven-central/v/io.github.tarifchakder.materializekmp/material-theme)](https://central.sonatype.com/artifact/io.github.tarifchakder.materializekmp/material-theme)  
[![License](https://img.shields.io/github/license/tarifchakder/MaterializeKMP)](LICENSE)  
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.10-blue.svg?logo=kotlin)](https://kotlinlang.org)  
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.3-blue)](https://github.com/JetBrains/compose-multiplatform)

![badge-android](https://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](https://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](https://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](https://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)

---

## ✨ Overview

**KMP Material Theme** is a [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) library that provides a **Material Design 3–based theme system** and essential components.

It enables consistent UI styling across multiple platforms:

- 📱 **Android**
- 🍏 **iOS**
- 💻 **JVM (Desktop)**
- 🌐 **JavaScript / Wasm (Browser)**

---
## 🎬 Demo

<p align="center">
  <strong>iOS Preview</strong><br>
  <img src="screenshot/ios.gif" alt="iOS Demo" width="300" height="400">
</p>

<p align="center">
  <strong>Web Preview</strong><br>
  <img src="screenshot/web_demo.gif" alt="Web Demo" width="300" height="500">
</p>

---

## ⚙️ Installation

### Gradle (Multiplatform)

Add the dependency to your `commonMain` source set:

```kotlin
commonMain {
    dependencies {
        implementation("io.github.tarifchakder.materializekmp:material-theme:1.0.3")
    }
}
```

### Version Catalog

```toml
[versions]
materialtheme = "1.0.3"

[libraries]
material-theme = { module = "io.github.tarifchakder.materializekmp:material-theme", version.ref = "materialtheme" }
```

# Usage
Please check sample App.kt and DynamicTheme.kt class

## 📦 Features
•	🎨 Material Design 3 theming
•	🌙 Dark & light themes
•	🖌️ Dynamic color palette support
•	⚡ Optimized for Compose Multiplatform (Android, iOS, Desktop, Web/Wasm)
•	🔧 Easy integration with Gradle Version Catalogs

## License
MaterializeKMP is licensed under the MIT License.
This library draws inspiration from the Material Color Palette by the Material Foundation. For details on their licensing, see their LICENSE.
[LICENSE](material-theme-kit/src/commonMain/kotlin/io/github/tarifchakder/materializekmp/materialcolor/LICENSE)

## Contributing
Contributions are welcome! Please check out the contributing guidelines for more information on how to get involved.



