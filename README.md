# KMP Material Theme

It is a Compose Multiplatform library targeting Android, iOS, Web, Desktop and designed to provide a Material Design 3 based theme, along with essential components for easy configuration

## Support Platforms

This library is written for Compose Multiplatform, and can be used on the following platforms:

- Android
- iOS
- JVM (Desktop)
- JavaScript/wasm (Browser)

## Setup

You can add this library to your project using Gradle.

### Multiplatform

To add to a multiplatform project, add the dependency to the common source-set:

```kotlin
kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("io.github.tarifchakder.materializekmp:material-theme:0.0.1")
            }
        }
    }
}
```

### Version Catalog

```toml
[versions]
materialtheme = "0.0.1"

[libraries]
materialKolor = { module = "io.github.tarifchakder.materializekmp", version.ref = "materialtheme" }
```

## License
For KMP support we use 
The module `material-color-kit` is licensed under the Apache License, Version 2.0. See
their [LICENSE](material-color-kit\src\commonMain\kotlin\io\github\tarifchakder\materialcolor\LICENSE) and their
their [LICENSE](material-color-kit\src\LICENSE) and their
repository [here](https://github.com/material-foundation/material-color-utilities) for more information.

For other LICENSE for this original REPOSITORY see [LICENSE](LICENSE) for more information.



