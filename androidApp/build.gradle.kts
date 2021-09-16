plugins {
    id(Plugins.androidApplication)
    id(Plugins.serialization)
    kotlin(Plugins.kotlinAndroid)
    id(Plugins.kotlinParcel)
    kotlin(Plugins.kapt)
    id(Plugins.safeArgs)
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        applicationId = "com.wind.book.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            *freeCompilerArgs.toTypedArray(),
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.Experimental",
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
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
    implementation(Deps.ImageLoader.transform)
    kapt(Deps.ImageLoader.compiler)
    implementation(Deps.ImageLoader.rcv) {
        isTransitive = false
    }
}
