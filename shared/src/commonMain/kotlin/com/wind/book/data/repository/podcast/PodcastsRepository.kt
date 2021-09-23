package com.wind.book.data.repository.podcast

import com.wind.book.data.repository.podcast.datasource.LocalPodcastsDataSource
import com.wind.book.data.repository.podcast.datasource.RemotePodcastsDataSource
import com.wind.book.model.Podcast

interface PodcastRepository {
    suspend fun getBestPodcast(currentPage: Int, region: String): List<Podcast>
}

internal class PodcastRepositoryImpl(
    private val remoteSource: RemotePodcastsDataSource,
    private val localSource: LocalPodcastsDataSource
) : PodcastRepository {

    override suspend fun getBestPodcast(currentPage: Int, region: String) =
        remoteSource.getBestPodcasts(currentPage, region)
}
