plugins {
    `android-app-plugin`
}

dependencies {
    implementation(projects.tvManiacAndroidApp.common.compose)
    implementation(projects.tvManiacShared.domain.persistence)
    implementation(libs.compose.activity)
}
