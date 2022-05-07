@file:Suppress("UnstableApiUsage")

import util.libs

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()
    ios()

    sourceSets.all {
        languageSettings.apply {
            optIn("kotlin.RequiresOptIn")
            optIn("kotlin.time.ExperimentalTime")
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
