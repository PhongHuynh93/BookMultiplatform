plugins {
    `android-app-plugin`
}

dependencies {
    implementation(projects.tvManiacAndroidApp.common.compose)
    implementation(projects.tvManiacShared.domain.persistence)
    implementation(projects.tvManiacShared.core.util)
    implementation(projects.tvManiacShared.sharedd)

    implementation(libs.compose.activity)
}
