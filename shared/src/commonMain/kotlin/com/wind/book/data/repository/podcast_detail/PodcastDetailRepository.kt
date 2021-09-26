package com.wind.book.data.repository.podcast_detail

import com.wind.book.data.repository.podcast_detail.datasource.RemotePodcastDetailDataSource
import com.wind.book.model.Podcast

interface PodcastDetailRepository {
    suspend fun getDetail(id: String, nextEpisodePubDate: Long, sort: String): Podcast
}

internal class PodcastDetailRepositoryImpl(
    private val remotePodcastDetailDataSource: RemotePodcastDetailDataSource
) :
    PodcastDetailRepository {

    override suspend fun getDetail(id: String, nextEpisodePubDate: Long, sort: String) =
        remotePodcastDetailDataSource.get(id, nextEpisodePubDate, sort)
}
