object Versions {
    const val kotlin = "1.6.10"
    const val coroutines = "1.6.0"
    const val kermit = "0.3.0-m1"
    const val ktor = "2.0.0-beta-1"
    const val junit = "4.13.2"
    const val material = "1.6.0-alpha01"
    const val multiplatformSettings = "0.8.1"
    const val sqlDelight = "1.5.0"
    const val serialization = "1.2.2"
    const val kotlinxDateTime = "0.3.1"
    const val asyncImage = "2.0.0-alpha06"
    const val uuid = "0.3.1"

    object Test {
        const val test = "1.4.1-alpha03"
        const val mockKotlin = "2.2.0"
        const val truth = "1.1.3"
    }
}

object Deps {
    const val junit = "junit:junit:${Versions.junit}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    const val multiplatformSettings =
        "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    const val multiplatformSettingsTest =
        "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
    const val uuid = "com.benasher44:uuid:${Versions.uuid}"

    object AndroidXTest {
        const val core = "androidx.test:core:${Versions.Test.test}"
        const val mockKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Test.mockKotlin}"
        const val truth = "com.google.truth:truth:${Versions.Test.truth}"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations =
            "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        const val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object SqlDelight {
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val coroutinesExtensions =
            "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    }

    object Ktor {
        const val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val commonJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val commonLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val negotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"

        const val androidCore = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val jvm = "io.ktor:ktor-client-jvm:${Versions.ktor}"
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
    const val googleService = "com.google.gms.google-services"
    const val crashlytics = "com.google.firebase.crashlytics"
}
