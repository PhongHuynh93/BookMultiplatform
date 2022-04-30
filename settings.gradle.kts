pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "BookMultiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")
include(":androidApp")
include(":composeApp")
include(":shared")
include(":ui-music")
include(":common-ui-view")
include(":wallUp")
include(":touchdown")
include(":tivi")
include(":common-imageloading")
include(":tmdb")
