package com.wind.book.data.repository.podcast_detail

import com.wind.book.data.mapping.PodcastMapper
import com.wind.book.data.model.dto.PodcastDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.data.util.Constant
import com.wind.book.model.Podcast
import io.ktor.client.request.header
import io.ktor.client.request.parameter

interface PodcastDetailAPI {
    suspend fun get(id: String, nextEpisodePubDate: Long, sort: String): Podcast?
}

internal class PodcastDetailAPIImpl() : BaseAPI(), PodcastDetailAPI {
    private val podcastMapper = PodcastMapper()

    override val baseUrl: String
        get() = Constant.AUDIO_BOOK_HOST

    override suspend fun get(id: String, nextEpisodePubDate: Long, sort: String) =
        doGet<PodcastDto> {
            apiPath(Constant.PODCAST_PATH, id)
            parameter(Constant.NEXT_EP_PUB_DATE_QUERY, nextEpisodePubDate.toString())
            parameter(Constant.SORT_QUERY, sort)
            header(Constant.QUERY_TOKEN, Constant.API_TOKEN)
        }.let {
            podcastMapper.apply(it)
        }
}
