import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    id("org.jetbrains.kotlinx.kover") version "0.5.0-RC"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("com.autonomousapps.dependency-analysis") version ("1.0.0-rc05")
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath(libs.bundles.plugins)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = true

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
                "-opt-in=kotlin.RequiresOptIn"
            )
        }
    }
}

tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates").configure {
    rejectVersionIf {
        val current = DependencyUpdates.versionToRelease(currentVersion)
        // If we're using a SNAPSHOT, ignore since we must be doing so for a reason.
        if (current == ReleaseType.SNAPSHOT) {
            return@rejectVersionIf true
        }

        // Otherwise we reject if the candidate is more 'unstable' than our version
        val candidate = DependencyUpdates.versionToRelease(candidate.version)
        return@rejectVersionIf candidate.isLessStableThan(current)
    }
}

/**
 * Configuration for Dependency Analysis Gradle Plugin
 */
dependencyAnalysis {
    issues {
        all {
            ignoreKtx(true)

            onAny {
                severity("fail")
            }

            onRedundantPlugins {
                severity("fail")
            }

            onUnusedDependencies {
                exclude(
                    "org.jetbrains.kotlin:kotlin-stdlib-jdk8",
                    "androidx.core:core-ktx",
                    "javax.inject:javax.inject",
                    "com.google.dagger:hilt-compiler",
                )
            }

            onUsedTransitiveDependencies {
                exclude(
                    // added by the parcelize plugin
                    "org.jetbrains.kotlin:kotlin-parcelize-runtime",
                    "androidx.compose.runtime:runtime",
                )
            }

            onIncorrectConfiguration {
                exclude(
                    "javax.inject:javax.inject",
                    "com.google.dagger:hilt-compiler",
                    "androidx.compose.runtime:runtime",
                )
            }
        }
    }

    dependencies {

        bundle("kotlin-stdlib") {
            include("^org.jetbrains.kotlin:kotlin-stdlib.*")
        }

        bundle("androidx-compose-runtime") {
            includeGroup("androidx.compose.runtime")
        }

        bundle("androidx-compose-ui") {
            includeGroup("androidx.compose.ui")
        }

        bundle("androidx-compose-foundation") {
            includeGroup("androidx.compose.animation")
            includeGroup("androidx.compose.foundation")
        }

        bundle("androidx-compose-material") {
            includeGroup("androidx.compose.material")
        }

        bundle("androidx-lifecycle") {
            include("^androidx.lifecycle:lifecycle-common.*")
            include("^androidx.lifecycle:lifecycle-runtime.*")
        }

        bundle("coil") {
            includeDependency("io.coil-kt:coil-compose")
        }

        bundle("dagger") {
            includeDependency("javax.inject:javax.inject")
            includeDependency("com.google.dagger:hilt-android")
        }
    }
}

/**
 * Disable iosTest Task for now. Using mockk causes the build to fail. Revisit later.
 * Action:
 * - Resolve issue or replace dependency
 */
// project.gradle.startParameter.excludedTaskNames.addAll(
//    listOf(
//        "compileTestKotlinIosArm64",
//        "compileTestKotlinIosX64"
//    )
// )
