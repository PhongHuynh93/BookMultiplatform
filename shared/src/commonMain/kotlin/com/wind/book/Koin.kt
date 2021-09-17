package com.wind.book

import co.touchlab.kermit.Kermit
import com.wind.book.data.repository.book.bookModule
import com.wind.book.domain.domainModule
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.datetime.Clock
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

lateinit var koin: Koin
lateinit var log: Kermit
fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            domainModule,
            bookModule
        )
    }

    val koin = koinApplication.koin
//    val doOnStartup = koin.get<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
//    doOnStartup.invoke()
//
    log = koin.get<Kermit> { parametersOf(null) }
    val appInfo = koin.get<AppInfo>() // AppInfo is a Kotlin interface with separate Android and iOS implementations
    log.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

val json = kotlinx.serialization.json.Json {
    isLenient = true
    ignoreUnknownKeys = true
}

private val coreModule = module {
    single<Clock> {
        Clock.System
    }
    single<HttpClient> {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json = json)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        log.v("Network") { message }
                    }
                }
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }
    }
}

// Used in kermit to inject tag
internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module
