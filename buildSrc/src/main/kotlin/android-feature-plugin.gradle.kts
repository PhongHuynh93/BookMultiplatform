@file:Suppress("UnstableApiUsage")

import gradle.kotlin.dsl.accessors._f4957b8d8477a91d10b51f0c4822df8b.kotlinOptions
import org.gradle.api.JavaVersion
import util.libs

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    sourceSets {
        val androidTest by getting
        val test by getting
        androidTest.java.srcDirs("src/androidTest/kotlin")
        test.java.srcDirs("src/test/kotlin")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.plugin.multiplatform.compose.get()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":tvManiacAndroidApp:common:resources"))
}
