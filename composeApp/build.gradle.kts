plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("kotlin-parcelize")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared)
                //Compose UI
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.uiTooling)
                //Navigation
                implementation(libs.voyager.navigator)
                //DI
                implementation(libs.koin.core)
                //Coroutines
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
                //Compose Utils
                implementation(libs.coil.compose)
                implementation(libs.activity.compose)
                implementation(libs.accompanist.insets)
                implementation(libs.accompanist.swiperefresh)
                //Coroutines
                implementation(libs.kotlinx.coroutines.android)
                //DI
                implementation(libs.koin.android)
                //WorkManager
                implementation(libs.work.runtime.ktx)
            }
        }
        val jvmMain by getting {
            dependencies {
                //Compose UI
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

//android app
android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        applicationId = Configs.androidApplicationId
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


// desktop app
// ./gradlew :composeApp:run
compose.desktop {
    application {
        mainClass = "com.wind.book.desktopApp.AppKt"
        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg)
            packageName = "Book Multiplatform"
            packageVersion = "1.0"
            macOS {
                bundleID = "com.wind.book.desktopApp"
            }
        }
    }
}
