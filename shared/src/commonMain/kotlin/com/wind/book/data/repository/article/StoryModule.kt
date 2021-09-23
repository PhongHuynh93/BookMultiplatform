package com.wind.book.data.repository.article

import com.wind.book.data.repository.article.datasource.RemoteStoryDataSource
import com.wind.book.data.repository.article.datasource.RemoteStoryDataSourceImpl
import org.koin.dsl.module

internal val storyModule = module {
    single<StoryAPI> {
        StoryAPIImpl(get())
    }

    single<RemoteStoryDataSource> {
        RemoteStoryDataSourceImpl(get())
    }

    single<StoryRepository> {
        StoryRepositoryImpl(get())
    }
}
