package com.wind.book.data.repository.podcast

import com.wind.book.data.repository.podcast.datasource.LocalPodcastDataSource
import com.wind.book.data.repository.podcast.datasource.RemotePodcastDataSource
import com.wind.book.model.Podcast

interface PodcastRepository {
    suspend fun getBestPodcast(currentPage: Int, region: String): List<Podcast>
}

internal class PodcastRepositoryImpl(
    private val remoteSource: RemotePodcastDataSource,
    private val localSource: LocalPodcastDataSource
) : PodcastRepository {

    override suspend fun getBestPodcast(currentPage: Int, region: String) =
        remoteSource.getBestPodcasts(currentPage, region)
}
