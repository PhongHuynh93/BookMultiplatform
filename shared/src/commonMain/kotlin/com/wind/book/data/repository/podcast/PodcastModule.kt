package com.wind.book.data.repository.podcast

import com.wind.book.data.repository.podcast.datasource.LocalPodcastDataSource
import com.wind.book.data.repository.podcast.datasource.LocalPodcastDataSourceImpl
import com.wind.book.data.repository.podcast.datasource.RemotePodcastDataSource
import com.wind.book.data.repository.podcast.datasource.RemotePodcastDataSourceImpl
import org.koin.dsl.module

internal val podcastModule = module {
    single<PodcastAPI> {
        PodcastAPIImpl(get())
    }
    single<LocalPodcastDataSource> {
        LocalPodcastDataSourceImpl()
    }
    single<RemotePodcastDataSource> {
        RemotePodcastDataSourceImpl(get())
    }
    single<PodcastRepository> {
        PodcastRepositoryImpl(get(), get())
    }
}
