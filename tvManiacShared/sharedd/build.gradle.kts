
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import util.libs

plugins {
    `kmm-domain-plugin`
    kotlin("plugin.serialization") version (libs.versions.kotlin.get())
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

version = libs.versions.shared.module.version.get()

kotlin {

    val xcf = XCFramework()
    ios {
        binaries.framework {
            baseName = "TvManiac"
            xcf.add(this)
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
            linkerOpts.add("-lsqlite3")

            export(projects.tvManiacShared.core.ui)
            export(projects.tvManiacShared.core.util)
//            export(projects.tvManiacShared.database)
//            export(projects.tvManiacShared.remote)
//            export(projects.tvManiacShared.domain.showDetails.api)
//            export(projects.tvManiacShared.domain.seasons.api)
//            export(projects.tvManiacShared.domain.episodes.api)
//            export(projects.tvManiacShared.domain.genre.api)
//            export(projects.tvManiacShared.domain.lastAirEpisodes.api)
//            export(projects.tvManiacShared.domain.similar.api)
//            export(projects.tvManiacShared.domain.seasonEpisodes.api)
//            export(projects.tvManiacShared.domain.showCommon.api)
//            export(projects.tvManiacShared.domain.discover.api)
            export(projects.tvManiacShared.domain.persistence)
            embedBitcode(BitcodeEmbeddingMode.BITCODE)

            transitiveExport = true
        }
    }
}

dependencies {
    commonMainApi(projects.tvManiacShared.core.ui)
    commonMainApi(projects.tvManiacShared.core.util)
//    commonMainApi(projects.tvManiacShared.database)
//    commonMainApi(projects.tvManiacShared.remote)
//    commonMainApi(projects.tvManiacShared.domain.showDetails.api)
//    commonMainApi(projects.tvManiacShared.domain.seasons.api)
//    commonMainApi(projects.tvManiacShared.domain.episodes.api)
//    commonMainApi(projects.tvManiacShared.domain.genre.api)
//    commonMainApi(projects.tvManiacShared.domain.lastAirEpisodes.api)
//    commonMainApi(projects.tvManiacShared.domain.similar.api)
//    commonMainApi(projects.tvManiacShared.domain.seasonEpisodes.api)
//    commonMainApi(projects.tvManiacShared.domain.showCommon.api)
//    commonMainApi(projects.tvManiacShared.domain.discover.api)
    commonMainApi(projects.tvManiacShared.domain.persistence)

//    commonMainImplementation(projects.tvManiacShared.domain.episodes.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.showDetails.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.seasons.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.genre.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.lastAirEpisodes.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.similar.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.seasonEpisodes.implementation)
//    commonMainImplementation(projects.tvManiacShared.domain.discover.implementation)

    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kermit)
}

multiplatformSwiftPackage {
    packageName("TvManiac")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }

    distributionMode { local() }
    outputDirectory(File("$projectDir/../../../", "tvmaniac-swift-packages"))
}
