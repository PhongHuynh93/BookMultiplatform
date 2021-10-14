package com.wind.book.data.repository.music.genre.datasource

import com.wind.book.data.repository.music.genre.GenreAPI
import com.wind.book.model.music.Artist
import com.wind.book.model.music.Genre

interface RemoteGenreDataSource {
    suspend fun getGenreList(index: Int, limit: Int): List<Genre>
    suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist>
}

internal class RemoteGenreDataSourceImpl(private val genreAPI: GenreAPI) : RemoteGenreDataSource {
    override suspend fun getGenreList(index: Int, limit: Int): List<Genre> {
        return genreAPI.getGenreList(index, limit)
    }

    override suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist> {
        return genreAPI.getArtistList(genreId, index, limit)
    }
}
