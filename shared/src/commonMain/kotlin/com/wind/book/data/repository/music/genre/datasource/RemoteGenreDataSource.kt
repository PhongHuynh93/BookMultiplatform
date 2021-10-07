package com.wind.book.data.repository.music.genre.datasource

import com.wind.book.data.repository.music.genre.GenreAPI
import com.wind.book.model.music.Genre

interface RemoteGenreDataSource {
    suspend fun getGenreList(): List<Genre>
}

internal class RemoteGenreDataSourceImpl(private val genreAPI: GenreAPI) : RemoteGenreDataSource {
    override suspend fun getGenreList(): List<Genre> {
        return genreAPI.get()
    }
}
