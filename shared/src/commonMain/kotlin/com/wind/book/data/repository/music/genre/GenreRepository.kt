package com.wind.book.data.repository.music.genre

import com.wind.book.data.repository.music.genre.datasource.LocalGenreDataSource
import com.wind.book.data.repository.music.genre.datasource.RemoteGenreDataSource
import com.wind.book.model.music.Artist
import com.wind.book.model.music.Genre

interface GenreRepository {
    suspend fun getGenreList(index: Int, limit: Int): List<Genre>
    suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist>
}

internal class GenreRepositoryImpl(
    private val remoteSource: RemoteGenreDataSource,
    private val localSource: LocalGenreDataSource
) : GenreRepository {

    override suspend fun getGenreList(index: Int, limit: Int): List<Genre> {
        return remoteSource.getGenreList(index, limit)
    }

    override suspend fun getArtistList(genreId: String, index: Int, limit: Int): List<Artist> {
        return remoteSource.getArtistList(genreId, index, limit)
    }
}
