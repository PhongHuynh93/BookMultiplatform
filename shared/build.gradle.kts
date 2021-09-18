plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("kotlinx-serialization")
}

// Use version for cocoaPod
version = "1.0"

kotlin {
    android()

    // Revert to just ios() when gradle plugin can properly resolve it
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    cocoapods {
        summary = "Common library for the BookMultiplatform"
        homepage = "https://github.com/PhongHuynh93/BookMultiplatform"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
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
                // Coroutines
                implementation(Deps.Coroutines.android)
                // ViewModel
                implementation(Deps.AndroidX.viewModel)
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
                // Coroutines
                implementation(Deps.Coroutines.common) {
                    version {
                        strictly(Versions.coroutines)
                    }
                }
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
