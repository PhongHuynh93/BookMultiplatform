@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import util.libs

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        applicationId = "org.shared.tvmaniac"
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName =
                    "app-${variant.baseName}-${variant.buildType.name}-${variant.versionName}.apk"
            }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.plugin.multiplatform.compose.get()
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    dependencies {
        implementation(libs.bundles.koin)
    }
}
