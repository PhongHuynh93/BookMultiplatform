package com.wind.book.data.repository.podcast

import com.wind.book.data.mapping.PodcastMapper
import com.wind.book.data.model.dto.PodcastListDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.data.util.Constant
import com.wind.book.model.Podcast
import io.ktor.client.request.header
import io.ktor.client.request.parameter

// Follow the CRUD name create-read-update-delete
interface PodcastAPI {
    suspend fun get(currentPage: Int, region: String): List<Podcast>
}

internal class PodcastAPIImpl : BaseAPI(), PodcastAPI {
    private val podcastMapper = PodcastMapper()

    override val baseUrl: String
        get() = Constant.AUDIO_BOOK_HOST

    override suspend fun get(currentPage: Int, region: String) =
        doGet<PodcastListDto> {
            apiPath(Constant.BEST_PODCAST_PATH)
            parameter("page", currentPage)
            parameter("region", region)
            header(Constant.QUERY_TOKEN, Constant.API_TOKEN)
        }.podcastDtos?.mapNotNull {
            podcastMapper.apply(it)
        } ?: emptyList()
}
