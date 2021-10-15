pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev"))
        maven(uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven"))
        maven(uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap"))
    }
}

rootProject.name = "BookMultiplatform"
include(":androidApp")
include(":shared")
