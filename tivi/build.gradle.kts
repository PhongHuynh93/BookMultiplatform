plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.kotlinParcel)
    id("org.jetbrains.compose")
    kotlin(Plugins.kapt)
    id("kotlinx-serialization")
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.androidApplicationId
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        freeCompilerArgs = listOf(
            *freeCompilerArgs.toTypedArray(),
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlin.Experimental",
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.ui.unit.ExperimentalUnitApi",
            "-opt-in=coil.annotation.ExperimentalCoilApi",
            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
        )
    }
}

dependencies {
    implementation(projects.shared)
    implementation(projects.base)
    implementation(projects.tiviUiSettings)
    implementation(projects.baseAndroid)
    implementation(projects.tmdb)
    // Compose UI
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.uiTooling)
    // DI
    implementation(libs.koin.core)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Compose Utils
    implementation(libs.coil.compose)
    implementation(libs.activity.compose)
    implementation(libs.icon.compose)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.insetsui)
    implementation(libs.accompanist.pager.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidX.material)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // DI
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
