plugins {
    id("com.android.library")
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    namespace = "org.shared.tvmaniac.resources"

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()
    }
}
