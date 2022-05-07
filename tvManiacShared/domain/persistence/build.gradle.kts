import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

dependencies {
    androidMainImplementation(libs.androidX.datastore)

    commonMainImplementation(projects.tvManiacShared.core.ui)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.koin.core)
}
