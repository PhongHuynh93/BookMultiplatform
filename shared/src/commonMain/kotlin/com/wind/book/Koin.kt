package com.wind.book

import co.touchlab.kermit.Kermit
import com.wind.book.data.repository.book.BookAPI
import com.wind.book.data.repository.book.BookAPIImpl
import com.wind.book.data.repository.book.bookModule
import com.wind.book.data.util.Constant
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.core.scope.get
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            bookModule
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
//    val doOnStartup = koin.get<() -> Unit>() // doOnStartup is a lambda which is implemented in Swift on iOS side
//    doOnStartup.invoke()
//
    val kermit = koin.get<Kermit> { parametersOf(null) }
    val appInfo = koin.get<AppInfo>() // AppInfo is a Kotlin interface with separate Android and iOS implementations
    kermit.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

private val coreModule = module {
    single<Clock> {
        Clock.System
    }
    single<HttpClient> {
        val log = get<Kermit>(Kermit::class.java)
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
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
