package com.wind.book.data.repository.music.genre

import com.wind.book.data.mapping.music.AlbumMapper
import com.wind.book.data.mapping.music.ArtistMapper
import com.wind.book.data.mapping.music.GenreMapper
import com.wind.book.data.model.dto.music.AlbumDto
import com.wind.book.data.model.dto.music.ArtistDto
import com.wind.book.data.model.dto.music.GenreDto
import com.wind.book.data.model.dto.music.MusicDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.model.music.Album
import com.wind.book.model.music.Artist
import com.wind.book.model.music.Genre
import io.ktor.client.request.parameter

interface GenreAPI {
    suspend fun getGenreList(index: Int, limit: Int): List<Genre>
    suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist>
    suspend fun getArtistAlbumList(id: String, index: Int, limit: Int): List<Album>
}

internal class GenreAPIImpl : BaseAPI(), GenreAPI {
    private val genreMapper = GenreMapper()
    private val artistMapper = ArtistMapper()
    private val albumMapper = AlbumMapper()

    override val baseUrl: String
        get() = "https://api.deezer.com/"

    override suspend fun getGenreList(index: Int, limit: Int): List<Genre> {
        return doGet<MusicDto<GenreDto>> {
            apiPath("genre")
            parameter("index", index)
            parameter("limit", limit)
        }.data?.mapNotNull {
            it?.let {
                genreMapper.apply(it)
            }
        } ?: emptyList()
    }

    override suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist> {
        return doGet<MusicDto<ArtistDto>> {
            apiPath("genre/$genreId/artists")
            parameter("index", index)
            parameter("limit", limit)
        }.data?.mapNotNull {
            it?.let {
                artistMapper.apply(it)
            }
        } ?: emptyList()
    }

    override suspend fun getArtistAlbumList(id: String, index: Int, limit: Int): List<Album> {
        return doGet<MusicDto<AlbumDto>> {
            apiPath("artist/$id/albums")
            parameter("index", index)
            parameter("limit", limit)
        }.data?.mapNotNull {
            it?.let {
                albumMapper.apply(it)
            }
        } ?: emptyList()
    }
}
