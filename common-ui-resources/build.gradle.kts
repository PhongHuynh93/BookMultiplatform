plugins {
    id(Plugins.androidLibrary)
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
    }
}
