import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id(Plugins.androidApplication)
    id(Plugins.serialization)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.kotlinParcel)
    kotlin(Plugins.kapt)
    id(Plugins.safeArgs)
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.androidApplicationId
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        versionCode = getVersionCodeX()
        versionName = getVersionNameX()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        // Disable lintVital. Not needed since lint is run on CI
        isCheckReleaseBuilds = false
        // Ignore any tests
        isIgnoreTestSources = true
        // Make the build fail on any lint errors
        isAbortOnError = true
        // Allow lint to check dependencies
        isCheckDependencies = true
    }

    // add compose
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

fun getVersionNameX(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd-HH"))
}

fun getVersionCodeX(): Int {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHH")).toInt()
}

dependencies {
    implementation(projects.shared)
    implementation(projects.uiMusic)
    implementation(projects.commonUiView)
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.koin)

    // Coroutines
    implementation(Deps.Coroutines.common)
    implementation(Deps.Coroutines.android)

    // Compose
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    implementation("androidx.compose.material3:material3:${Versions.material3}")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")
    implementation("androidx.compose.material:material-icons-extended:1.1.0-rc01")
    implementation("androidx.activity:activity-compose:1.4.0")
}
