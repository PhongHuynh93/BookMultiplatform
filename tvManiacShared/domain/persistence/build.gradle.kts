import util.libs

plugins {
    `kmm-domain-plugin`
}

android {
    namespace = "org.shared.tvmaniac.tvManiacShared.domain.persistence"
}

dependencies {
    androidMainImplementation(libs.androidX.datastore)

    commonMainImplementation(projects.tvManiacShared.core.ui)
    commonMainImplementation(libs.kotlinx.coroutines.core)
}
