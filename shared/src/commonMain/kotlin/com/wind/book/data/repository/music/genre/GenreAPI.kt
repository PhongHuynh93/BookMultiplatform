package com.wind.book.data.repository.music.genre

import com.wind.book.data.mapping.music.GenreMapper
import com.wind.book.data.model.dto.music.GenreDto
import com.wind.book.data.model.dto.music.MusicDto
import com.wind.book.model.music.Genre
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.takeFrom

interface GenreAPI {
    suspend fun get(): List<Genre>
}

internal class GenreAPIImpl(private val client: HttpClient) : GenreAPI {
    private val genreMapper = GenreMapper()

    private fun HttpRequestBuilder.apiUrl() {
        url {
            takeFrom("https://api.deezer.com/")
            encodedPath = "genre"
        }
    }

    override suspend fun get(): List<Genre> {
        return client.get<MusicDto<GenreDto>> {
            apiUrl()
        }.data?.mapNotNull {
            it?.let {
                genreMapper.apply(it)
            }
        } ?: emptyList()
    }
}
