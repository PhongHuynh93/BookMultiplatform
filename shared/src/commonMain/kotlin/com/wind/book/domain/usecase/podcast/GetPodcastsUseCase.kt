package com.wind.book.domain.usecase.podcast

import com.wind.book.data.repository.podcast.PodcastRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.Podcast
import com.wind.book.platformCoroutineDispatcher

class GetPodcastParam(val currentPage: Int, val region: String)

class GetPodcastsUseCase(private val podcastRepository: PodcastRepository) :
    UseCase<GetPodcastParam, List<Podcast>>(platformCoroutineDispatcher) {

    override suspend fun execute(parameters: GetPodcastParam) =
        podcastRepository.getBestPodcast(parameters.currentPage, parameters.region)
}
