package com.wind.book.data.repository.podcast

import com.wind.book.data.repository.podcast.datasource.LocalPodcastsDataSource
import com.wind.book.data.repository.podcast.datasource.LocalPodcastsDataSourceImpl
import com.wind.book.data.repository.podcast.datasource.RemotePodcastsDataSource
import com.wind.book.data.repository.podcast.datasource.RemotePodcastsDataSourceImpl
import org.koin.dsl.module

internal val podcastModule = module {
    single<PodcastAPI> {
        PodcastAPIImpl(get())
    }
    single<LocalPodcastsDataSource> {
        LocalPodcastsDataSourceImpl()
    }
    single<RemotePodcastsDataSource> {
        RemotePodcastsDataSourceImpl(get())
    }
    single<PodcastRepository> {
        PodcastRepositoryImpl(get(), get())
    }
}