package com.wind.book.data.repository.music.genre

import com.wind.book.data.repository.music.genre.datasource.LocalGenreDataSource
import com.wind.book.data.repository.music.genre.datasource.LocalGenreDataSourceImpl
import com.wind.book.data.repository.music.genre.datasource.RemoteGenreDataSource
import com.wind.book.data.repository.music.genre.datasource.RemoteGenreDataSourceImpl
import org.koin.dsl.module

internal val genreModule = module {
    single<GenreAPI> {
        GenreAPIImpl(get())
    }
    single<LocalGenreDataSource> {
        LocalGenreDataSourceImpl()
    }
    single<RemoteGenreDataSource> {
        RemoteGenreDataSourceImpl(get())
    }
    single<GenreRepository> {
        GenreRepositoryImpl(get(), get())
    }
}
