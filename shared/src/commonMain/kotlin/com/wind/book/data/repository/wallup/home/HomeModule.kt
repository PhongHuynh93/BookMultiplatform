package com.wind.book.data.repository.wallup.home

import com.wind.book.data.repository.wallup.home.datasource.RemoteHomeDataSource
import com.wind.book.data.repository.wallup.home.datasource.RemoteHomeDataSourceImpl
import org.koin.dsl.module

internal val homeModule = module {
    single<HomeAPI> {
        HomeAPIImpl()
    }
    single<RemoteHomeDataSource> {
        RemoteHomeDataSourceImpl(get())
    }
    single<HomeRepository> {
        HomeRepositoryImpl(get())
    }
}
