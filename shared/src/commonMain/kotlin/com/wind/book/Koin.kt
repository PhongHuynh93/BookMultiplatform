package com.wind.book

import co.touchlab.kermit.Kermit
import com.wind.book.data.repository.article.storyModule
import com.wind.book.data.repository.book.bookModule
import com.wind.book.data.repository.music.genre.genreModule
import com.wind.book.data.repository.podcast.podcastModule
import com.wind.book.data.repository.podcast_detail.podcastDetailModule
import com.wind.book.domain.domainModule
import com.wind.book.viewmodel.BaseViewModel
import com.wind.book.viewmodel.viewmodelModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import kotlin.reflect.KClass

lateinit var koin: Koin
lateinit var log: Kermit
lateinit var httpClient: HttpClient
fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            domainModule,
            viewmodelModule,
            bookModule,
            podcastModule,
            storyModule,
            podcastDetailModule,
            genreModule
        )
    }

    val koin = koinApplication.koin
    log = koin.get<Kermit> { parametersOf(null) }
    // FIXME: Bug in jvm
//    val appInfo = koin.get<AppInfo>() // AppInfo is a Kotlin interface with separate Android and iOS implementations
//    log.v { "App Id ${appInfo.appId}" }
    httpClient = koin.get()
    return koinApplication
}

val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

private val coreModule = module {
    single<CoroutineDispatcher> {
        platformCoroutineDispatcher
    }
    single<Clock> {
        Clock.System
    }
    single<HttpClient> {
        log.v("Init core init http client")
        HttpClient {
            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        log.v("Network message $message")
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

expect inline fun <reified T : BaseViewModel> Module.viewModelDefinition(
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>>

@Suppress("UNCHECKED_CAST")
fun <T> Koin.getDependency(clazz: KClass<*>): T {
    return get(clazz, null) { parametersOf(clazz.simpleName) } as T
}
