enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "BookMultiplatform"

// include(":androidApp")
// include(":composeApp")
// include(":shared")
// include(":ui-music")
// include(":common-ui-view")
// include(":wallUp")
// include(":touchdown")
include(":tvManiacAndroidApp:app")
include(":tvManiacAndroidApp:common:compose")
include(":tvManiacAndroidApp:common:resources")
include(":tvManiacShared:domain:persistence")
include(":tvManiacShared:core:ui")
include(":tvManiacShared:core:util")
include(":tvManiacShared:sharedd")
