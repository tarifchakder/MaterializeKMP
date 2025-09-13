@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradle.publish)
    signing
    alias(libs.plugins.dokka)
}

kotlin {
    androidTarget {
        compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17) }
    }

    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "material-theme"
        browser()
        binaries.executable()
    }
}

android {
    namespace = "io.github.tarifchakder.materializekmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.tarifchakder.materializekmp",
        artifactId = "material-theme",
        version = "1.0.6"
    )
    pom {
        name.set("MaterializeKMP")
        description.set("Dynamic Theme Manager: Essential Kotlin Multiplatform Library for Seamless Theming Across All Platforms")
        url.set("https://github.com/tarifchakder/MaterializeKMP")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("tarif")
                name.set("Tarif Chakder")
                email.set("tarifchakder@outlook.com")
            }
        }
        scm { url.set("https://github.com/tarifchakder/MaterializeKMP") }
    }

    publishToMavenCentral()
    signAllPublications()
}

signing {
    val signingKeyId: String? = findProperty("signingKeyId") as String?
    val signingPassword: String? = findProperty("signingPassword") as String?
    val signingKey: String? = findProperty("signingKey") as String?

    if (!signingKeyId.isNullOrBlank() && !signingPassword.isNullOrBlank() && !signingKey.isNullOrBlank()) {
        useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        sign(publishing.publications)
    } else {
        logger.warn("⚠️ No signing configuration found, skipping signing.")
    }
}