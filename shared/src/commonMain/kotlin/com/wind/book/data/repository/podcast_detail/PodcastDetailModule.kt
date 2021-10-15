package com.wind.book.data.repository.podcast_detail

import com.wind.book.data.repository.podcast_detail.datasource.RemotePodcastDetailDataSource
import com.wind.book.data.repository.podcast_detail.datasource.RemotePodcastDetailDataSourceImpl
import org.koin.dsl.module

internal val podcastDetailModule = module {
    single<PodcastDetailAPI> {
        PodcastDetailAPIImpl()
    }
    single<RemotePodcastDetailDataSource> {
        RemotePodcastDetailDataSourceImpl(get())
    }
    single<PodcastDetailRepository> {
        PodcastDetailRepositoryImpl(get())
    }
}
