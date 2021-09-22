package com.wind.book.data.repository.podcast.datasource

import com.wind.book.data.repository.podcast.PodcastAPI
import com.wind.book.model.Podcast

interface RemotePodcastsDataSource {
    suspend fun getBestPodcasts(currentPage: Int, region: String): List<Podcast>
}

internal class RemotePodcastsDataSourceImpl(private val podcastAPI: PodcastAPI) :
    RemotePodcastsDataSource {
    override suspend fun getBestPodcasts(currentPage: Int, region: String) = podcastAPI.get(currentPage, region)
}
