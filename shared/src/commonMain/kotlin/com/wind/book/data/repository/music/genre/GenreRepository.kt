package com.wind.book.data.repository.music.genre

import com.wind.book.data.repository.music.genre.datasource.LocalGenreDataSource
import com.wind.book.data.repository.music.genre.datasource.RemoteGenreDataSource
import com.wind.book.model.music.Genre

interface GenreRepository {
    suspend fun getGenreList(): List<Genre>
}

internal class GenreRepositoryImpl(
    private val remoteSource: RemoteGenreDataSource,
    private val localSource: LocalGenreDataSource
) : GenreRepository {
    override suspend fun getGenreList(): List<Genre> {
        return remoteSource.getGenreList()
    }
}
