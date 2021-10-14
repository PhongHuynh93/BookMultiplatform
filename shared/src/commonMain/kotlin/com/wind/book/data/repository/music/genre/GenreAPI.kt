package com.wind.book.data.repository.music.genre

import com.wind.book.data.mapping.music.ArtistMapper
import com.wind.book.data.mapping.music.GenreMapper
import com.wind.book.data.model.dto.music.ArtistDto
import com.wind.book.data.model.dto.music.GenreDto
import com.wind.book.data.model.dto.music.MusicDto
import com.wind.book.model.music.Artist
import com.wind.book.model.music.Genre
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom

interface GenreAPI {
    suspend fun getGenreList(index: Int, limit: Int): List<Genre>
    suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist>
}

internal class GenreAPIImpl(private val client: HttpClient) : GenreAPI {
    private val genreMapper = GenreMapper()
    private val artistMapper = ArtistMapper()

    private fun HttpRequestBuilder.apiUrl(path: String = "genre") {
        url {
            takeFrom("https://api.deezer.com/")
            encodedPath = path
        }
    }

    override suspend fun getGenreList(index: Int, limit: Int): List<Genre> {
        return client.get<MusicDto<GenreDto>> {
            apiUrl()
            url {
                parameter("index", index)
                parameter("limit", limit)
            }
        }.data?.mapNotNull {
            it?.let {
                genreMapper.apply(it)
            }
        } ?: emptyList()
    }

    override suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist> {
        return client.get<MusicDto<ArtistDto>> {
            apiUrl("genre/$genreId/artists")
            url {
                parameter("index", index)
                parameter("limit", limit)
            }
        }.data?.mapNotNull {
            it?.let {
                artistMapper.apply(it)
            }
        } ?: emptyList()
    }
}
