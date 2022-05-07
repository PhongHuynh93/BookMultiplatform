package org.shared.shared

import co.touchlab.kermit.Kermit
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf

lateinit var koin: Koin
lateinit var log: Kermit
fun initKoin(vararg appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            *appModule
        )
    }

    val koin = koinApplication.koin
    log = koin.get<Kermit> { parametersOf(null) }
    // FIXME: Bug in jvm
//    val appInfo = koin.get<AppInfo>() // AppInfo is a Kotlin interface with separate Android and iOS implementations
//    log.v { "App Id ${appInfo.appId}" }
    return koinApplication
}
