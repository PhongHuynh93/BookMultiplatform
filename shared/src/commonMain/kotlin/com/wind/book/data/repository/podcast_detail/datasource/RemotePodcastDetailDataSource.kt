package com.wind.book.data.repository.podcast_detail.datasource

import com.wind.book.data.repository.podcast_detail.PodcastDetailAPI
import com.wind.book.model.Podcast

interface RemotePodcastDetailDataSource {
    suspend fun get(id: String, nextEpisodePubDate: Long, sort: String): Podcast
}

internal class RemotePodcastDetailDataSourceImpl(
    private val podcastDetailAPI: PodcastDetailAPI
) :
    RemotePodcastDetailDataSource {

    override suspend fun get(id: String, nextEpisodePubDate: Long, sort: String) =
        podcastDetailAPI.get(id, nextEpisodePubDate, sort)!!
}
