# KMP Material Theme

![Maven Central](https://img.shields.io/maven-central/v/io.github.tarifchakder.materializekmp/material-theme)
[![License](https://img.shields.io/github/license/tarifchakder/MaterializeKMP)](https://opensource.org/license/mit/)

[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)

[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.7.0-blue)](https://github.com/JetBrains/compose-multiplatform)
![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js%2Fwasm-FDD835.svg?style=flat)

It is a Compose Multiplatform library targeting Android, iOS, Web, Desktop and designed to provide a Material Design 3 based theme, along with essential components for easy configuration

## Support Platforms

This library is written for Compose Multiplatform, and can be used on the following platforms:

- Android
- iOS
- JVM (Desktop)
- JavaScript/wasm (Browser)

## DEMO

![WEB](screenshot/web_demo.gif)


## Configuration

You can add this library to your project using Gradle.

### Multiplatform

To add to a multiplatform project, add the dependency to the common source-set:

```kotlin
  commonMain {
    dependencies {
        implementation("io.github.tarifchakder.materializekmp:material-theme:0.0.1")
    }
  }
```

### Version Catalog

```toml
[versions]
materialtheme = "0.0.1"

[libraries]
material-theme = { module = "io.github.tarifchakder.materializekmp:material-theme", version.ref = "materialtheme" }
```

## License
For KMP support we use 
The module `material-color-kit` is licensed under the Apache License, Version 2.0. See
their [LICENSE](material-color-kit\src\commonMain\kotlin\io\github\tarifchakder\materialcolor\LICENSE) and their
their [LICENSE](material-color-kit\src\LICENSE) and their
repository [here](https://github.com/material-foundation/material-color-utilities) for more information.

For other LICENSE for this original REPOSITORY see [LICENSE](LICENSE) for more information.



