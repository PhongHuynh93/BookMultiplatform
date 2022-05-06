@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("android")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.plugin.multiplatform.compose.get()
    }
}

dependencies {
    implementation(project(":tvManiacAndroidApp:common:resources"))
    api(compose.runtime)
    api(compose.foundation)
    api(compose.material)
    api(compose.ui)
    api(compose.uiTooling)
    api(compose.materialIconsExtended)
    api(libs.bundles.accompanist)
}
