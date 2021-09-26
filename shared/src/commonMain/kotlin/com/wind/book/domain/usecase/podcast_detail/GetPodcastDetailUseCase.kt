package com.wind.book.domain.usecase.podcast_detail

import com.wind.book.data.repository.podcast_detail.PodcastDetailRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.Podcast
import com.wind.book.platformCoroutineDispatcher

class GetPodcastDetailParam(val id: String, val nextEpisodePubDate: Long, val sort: String)

class GetPodcastDetailUseCase(
    private val podcastDetailRepository: PodcastDetailRepository
) :
    UseCase<GetPodcastDetailParam, Podcast>(platformCoroutineDispatcher) {

    override suspend fun execute(parameters: GetPodcastDetailParam) =
        podcastDetailRepository.getDetail(
            parameters.id,
            parameters.nextEpisodePubDate,
            parameters.sort
        )
}
