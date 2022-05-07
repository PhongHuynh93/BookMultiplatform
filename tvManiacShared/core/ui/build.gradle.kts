import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    namespace = "org.shared.tvmaniac.tvManiacShared.core.ui"
}

dependencies {
    androidMainImplementation(projects.tvManiacShared.core.util)

    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.koin.core)
}
