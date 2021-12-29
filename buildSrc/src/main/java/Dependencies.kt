object Versions {
    const val kotlin = "1.6.0"
    const val gradlePlugin = "7.0.4"
    const val coroutines = "1.5.1-new-mm-dev2"
    const val kermit = "0.3.0-m1"
    const val koin = "3.1.4"
    const val ktor = "1.6.2-native-mm-eap-196"
    const val junit = "4.13.2"
    const val material = "1.3.0"
    const val material3 = "1.0.0-alpha02"
    const val multiplatformSettings = "0.8"
    const val sqlDelight = "1.5.0"
    const val serialization = "1.2.2"
    const val kotlinxDateTime = "0.3.1"
    const val parcelable = "0.1.4"
    const val ktlint = "10.2.0"
    const val asyncImage = "1.4.0"
    const val compose = "1.1.0-rc01"

    object AndroidX {
        const val supportLibrary: String = "1.2.0"
        const val material: String = "1.3.0"
        const val constraintLayout: String = "2.1.0"
        const val rcv = "1.2.1"
        const val swipeToRefresh = "1.1.0"
        const val vPager = "1.1.0-beta01"
        const val coreKtx = "1.3.2"
        const val fragmentKtx = "1.3.3"
        const val activityKtx = "1.2.2"
        const val lifeCycleKtx = "2.4.0-alpha03"
        const val test = "1.3.0"
        const val test_ext = "1.1.2"
        const val nav = "2.4.0-alpha06"
    }
}

object Deps {
    const val junit = "junit:junit:${Versions.junit}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinTest = "io.insert-koin:koin-test:${Versions.koin}"
    const val multiplatformSettings = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    const val multiplatformSettingsTest = "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
    const val parcelable = "com.arkivanov.essenty:parcelable:${Versions.parcelable}"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.supportLibrary}"
        const val material = "com.google.android.material:material:${Versions.AndroidX.material}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.AndroidX.rcv}"
        const val swipeToRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swipeToRefresh}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val viewPager = "androidx.viewpager2:viewpager2:${Versions.AndroidX.vPager}"
        const val core = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragmentKtx}"
        const val activity = "androidx.activity:activity-ktx:${Versions.AndroidX.activityKtx}"
        const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifeCycleKtx}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifeCycleKtx}"
        const val lifeCycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.AndroidX.lifeCycleKtx}"
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.nav}"
        const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.nav}"
    }

    object AndroidXTest {
        const val core = "androidx.test:core:${Versions.AndroidX.test}"
        const val junit = "androidx.test.ext:junit:${Versions.AndroidX.test_ext}"
        const val runner = "androidx.test:runner:${Versions.AndroidX.test}"
        const val rules = "androidx.test:rules:${Versions.AndroidX.test}"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        const val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object SqlDelight {
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    }

    object Ktor {
        const val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val commonJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val commonLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val androidCore = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val commonSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    }

    object ImageLoader {
        const val core = "io.coil-kt:coil:${Versions.asyncImage}"
    }
}

object Configs {
    const val minSdk = 23
    const val compileSdk = 31
    const val targetSdk = 31

    const val androidApplicationId = "com.wind.book.android"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "android"
    const val kotlinParcel = "kotlin-parcelize"
    const val kapt = "kapt"
    const val serialization = "kotlinx-serialization"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    const val googleService = "com.google.gms.google-services"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val detekt = "io.gitlab.arturbosch.detekt"
}

object ClassPaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.nav}"
}
