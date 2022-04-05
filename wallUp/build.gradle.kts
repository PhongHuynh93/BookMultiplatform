plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.kotlinParcel)
    id("org.jetbrains.compose")
    kotlin(Plugins.kapt)
    id("com.google.devtools.ksp") version "1.6.20-1.0.4"
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
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
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        freeCompilerArgs = listOf(
            *freeCompilerArgs.toTypedArray(),
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.Experimental",
            "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-Xopt-in=androidx.compose.ui.unit.ExperimentalUnitApi",
            "-Xopt-in=coil.annotation.ExperimentalCoilApi",
            "-Xopt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
        )
    }
}

dependencies {
    implementation(projects.shared)
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
    implementation(libs.androidX.material)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // DI
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    // Nav Host
    implementation("com.google.accompanist:accompanist-navigation-animation:0.23.1")
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.3.4-beta")
    implementation("io.github.raamcosta.compose-destinations:core:1.3.4-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.3.4-beta")
}
