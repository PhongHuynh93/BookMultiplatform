package com.wind.book.data.repository.podcast.datasource

import com.wind.book.data.repository.podcast.PodcastAPI
import com.wind.book.model.Podcast

interface RemotePodcastDataSource {
    suspend fun getBestPodcasts(currentPage: Int, region: String): List<Podcast>
}

internal class RemotePodcastDataSourceImpl(private val podcastAPI: PodcastAPI) :
    RemotePodcastDataSource {
    override suspend fun getBestPodcasts(currentPage: Int, region: String) = podcastAPI.get(currentPage, region)
}
