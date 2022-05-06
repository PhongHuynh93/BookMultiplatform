plugins {
    `android-app-plugin`
}

android {
    namespace = "org.shared.tvmaniac"
}

dependencies {
    implementation(projects.tvManiacAndroidApp.common.compose)
    implementation(libs.compose.activity)
}
