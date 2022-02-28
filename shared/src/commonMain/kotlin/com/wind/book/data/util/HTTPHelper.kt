package com.wind.book.data.util

import com.wind.book.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.utils.EmptyContent
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

class HTTPHelper {

    suspend inline fun <reified T> doGet(
        requestBuilder: HttpRequestBuilder.() -> Unit
    ): T {
        return httpClient.get {
            apply(requestBuilder)
        }.body()
    }

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T> doPost(
        requestBody: Any = EmptyContent,
        requestBuilder: HttpRequestBuilder.() -> Unit
    ): T {
        return httpClient.post {
            this.body = requestBody
            apply(requestBuilder)
            contentType(ContentType.Application.Json)
        }.body()
    }
}
