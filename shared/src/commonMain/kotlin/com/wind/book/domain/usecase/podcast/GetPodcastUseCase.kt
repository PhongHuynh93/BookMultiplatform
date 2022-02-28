package com.wind.book.domain.usecase.podcast

import com.wind.book.data.repository.podcast.PodcastRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.Podcast
import com.wind.book.platformCoroutineDispatcher

class GetPodcastListParam(val currentPage: Int, val region: String)

class GetPodcastUseCase(private val podcastRepository: PodcastRepository) :
    UseCase<GetPodcastListParam, List<Podcast>>(platformCoroutineDispatcher) {

    override suspend fun execute(parameters: GetPodcastListParam) =
        podcastRepository.getBestPodcast(parameters.currentPage, parameters.region)
}
