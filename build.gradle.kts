import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jetbrains.kotlinx.kover") version "0.5.0-RC"
    id("com.github.ben-manes.versions") version "0.39.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    with(ClassPaths) {
        dependencies {
            classpath(gradlePlugin)
            classpath(kotlinPlugin)
            classpath(safeArgs)
            classpath(serialization)
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven"))
        maven(uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap"))
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        verbose.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
        disabledRules.set(setOf("experimental:annotation"))
    }

    afterEvaluate {
        tasks.named("check").configure {
            dependsOn(tasks.getByName("ktlintCheck"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            // Treat all Kotlin warnings as errors
            allWarningsAsErrors = true

            // Set JVM target to 1.8
            jvmTarget = "1.8"

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
                "-Xopt-in=kotlin.RequiresOptIn"
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
