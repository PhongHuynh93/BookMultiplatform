import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

dependencies {
    androidMainImplementation(projects.tvManiacShared.core.util)

    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.koin.core)
}
