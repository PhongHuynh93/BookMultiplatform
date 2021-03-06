package com.wind.book.data.util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.utils.EmptyContent
import io.ktor.http.path
import io.ktor.http.takeFrom

abstract class BaseAPI {
    abstract val baseUrl: String
    val httpHelper = HTTPHelper()

    protected fun HttpRequestBuilder.apiPath(vararg components: String) {
        url {
            takeFrom(baseUrl)
            path(*components)
        }
    }

    suspend inline fun <reified T> doGet(
        requestBuilder: HttpRequestBuilder.() -> Unit
    ): T {
        return tryCatch {
            httpHelper.doGet {
                apply(requestBuilder)
            }
        }
    }

    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        requestBuilder: HttpRequestBuilder.() -> Unit
    ): T {
        return tryCatch {
            httpHelper.doPost(requestBody) {
                apply(requestBuilder)
            }
        }
    }

    inline fun <T> tryCatch(action: () -> T): T {
        return try {
            action()
        } catch (e: Throwable) {
            // TODO: handle no connection here
            throw UnknownFailure
        }
    }
}
