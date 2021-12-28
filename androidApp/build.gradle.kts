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
    implementation(project(":shared"))

    // UI
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.swipeToRefresh)
    implementation(Deps.AndroidX.constraint)
    implementation(Deps.AndroidX.viewPager)
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.fragment)
    implementation(Deps.AndroidX.activity)
    implementation(Deps.AndroidX.lifeCycle)
    implementation(Deps.AndroidX.viewModel)
    implementation(Deps.AndroidX.lifeCycleCommon)
    implementation(Deps.AndroidX.navFragment)
    implementation(Deps.AndroidX.navUI)

    // Coroutines
    implementation(Deps.Coroutines.common)
    implementation(Deps.Coroutines.android)

    // DI
    implementation(Deps.koinCore)
    implementation(Deps.koinAndroid)

    // Image load
    implementation(Deps.ImageLoader.core)

    // Compose
    implementation("androidx.compose.runtime:runtime:${Versions.compose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation:${Versions.compose}")
    implementation("androidx.compose.foundation:foundation-layout:${Versions.compose}")
    implementation("androidx.compose.material3:material3:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    implementation("com.google.android.material:compose-theme-adapter:${Versions.compose}")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")
    implementation("androidx.compose.material:material-icons-extended:${Versions.compose}")
    implementation("androidx.activity:activity-compose:1.4.0")
}
