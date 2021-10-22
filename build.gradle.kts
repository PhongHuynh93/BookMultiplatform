import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlint
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
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
        disabledRules.set(setOf("no-wildcard-imports", "experimental:annotation"))
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

            freeCompilerArgs = listOf(
                *freeCompilerArgs.toTypedArray(),
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
            )
        }
    }
}
