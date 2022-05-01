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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }
    jvm()

    cocoapods {
        summary = "Common library for the BookMultiplatform"
        homepage = "https://github.com/PhongHuynh93/BookMultiplatform"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            isStatic = false
            export(projects.commonModel)
        }
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
                implementation(Deps.Ktor.negotiation)
                api(projects.commonModel)

                // Coroutines
                implementation(Deps.Coroutines.common)
                // Logger
                api(Deps.kermit)
                // Key-Value storage
                implementation(Deps.multiplatformSettings)
                // Injection
                implementation(libs.koin.core)
                // Date-time
                implementation(Deps.kotlinxDateTime)
                // uuid
                implementation(Deps.uuid)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.koin.test)
                implementation(Deps.multiplatformSettingsTest)
                implementation(Deps.Coroutines.test)
            }
        }
        val androidMain by getting {
            dependencies {
                // Network
                implementation(Deps.Ktor.androidCore)
                // ViewModel
                implementation(libs.androidX.viewModel)
                implementation(libs.koin.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Deps.AndroidXTest.core)
                implementation(Deps.AndroidXTest.mockKotlin)
                implementation(Deps.AndroidXTest.truth)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // Network
                implementation(Deps.Ktor.ios)
            }
        }
        val jvmMain by getting {
            dependencies {
                // Network
                implementation(Deps.Ktor.jvm)
            }
        }
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
