package com.wind.book.data.repository.book

import com.wind.book.data.repository.book.datasource.LocalBookDataSource
import com.wind.book.data.repository.book.datasource.LocalBookDataSourceImpl
import com.wind.book.data.repository.book.datasource.RemoteBookDataSource
import com.wind.book.data.repository.book.datasource.RemoteBookDataSourceImpl
import org.koin.dsl.module

internal val bookModule = module {
    single<BestSellerAPI> {
        BestSellerAPIImpl(get())
    }
    single<LocalBookDataSource> {
        LocalBookDataSourceImpl()
    }
    single<RemoteBookDataSource> {
        RemoteBookDataSourceImpl(get())
    }
    single<BookRepository> {
        BookRepositoryImpl(get(), get())
    }
}
