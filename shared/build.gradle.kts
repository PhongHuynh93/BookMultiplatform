plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

// Use version for cocoaPod
version = "1.0"

kotlin {
    android()

    ios {
        binaries.framework()
    }

    cocoapods {
        summary = "Common library for the BookMultiplatform"
        homepage = "https://github.com/PhongHuynh93/BookMultiplatform"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
        val commonMain by getting {
            dependencies {
                // Network
                implementation(Deps.Ktor.commonCore)
                implementation(Deps.Ktor.commonJson)
                implementation(Deps.Ktor.commonLogging)
                implementation(Deps.Ktor.commonSerialization)

                // Coroutines
                implementation(Deps.Coroutines.common)
                // Logger
                api(Deps.kermit)
                // Key-Value storage
                implementation(Deps.multiplatformSettings)
                // Injection
                implementation(Deps.koinCore)
                // Date-time
                implementation(Deps.kotlinxDateTime)
                // Parcelable
                implementation(Deps.parcelable)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Deps.koinTest)
                implementation(Deps.multiplatformSettingsTest)
            }
        }
        val androidMain by getting {
            dependencies {
                // Network
                implementation(Deps.Ktor.androidCore)
                // ViewModel
                implementation(Deps.AndroidX.viewModel)
                implementation(Deps.koinAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                // Coroutines
                implementation(Deps.Coroutines.test)
            }
        }
        val iosMain by getting {
            dependencies {
                // Network
                implementation(Deps.Ktor.ios)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
