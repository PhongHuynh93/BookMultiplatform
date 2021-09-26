package com.wind.book.data.repository.podcast_detail

import com.wind.book.data.mapping.PodcastMapper
import com.wind.book.data.model.dto.PodcastDto
import com.wind.book.data.util.Constant
import com.wind.book.model.Podcast
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface PodcastDetailAPI {
    suspend fun get(id: String, nextEpisodePubDate: Long, sort: String): Podcast?
}

internal class PodcastDetailAPIImpl(private val client: HttpClient) : PodcastDetailAPI {
    private val podcastMapper = PodcastMapper()

    override suspend fun get(id: String, nextEpisodePubDate: Long, sort: String) = client.get<PodcastDto> {
        url {
            takeFrom(Constant.AUDIO_BOOK_HOST)
            path(Constant.PODCAST_PATH, id)
            parameter(Constant.NEXT_EP_PUB_DATE_QUERY, nextEpisodePubDate.toString())
            parameter(Constant.SORT_QUERY, sort)
            header(Constant.QUERY_TOKEN, Constant.API_TOKEN)
        }
    }.let {
        podcastMapper.apply(it)
    }
}
