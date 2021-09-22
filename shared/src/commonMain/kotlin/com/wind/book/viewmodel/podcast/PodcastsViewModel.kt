package com.wind.book.viewmodel.podcast

import com.wind.book.domain.usecase.podcast.GetBestPodcastParam
import com.wind.book.domain.usecase.podcast.GetBestPodcastsUseCase
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.LoadMoreVM

class PodcastsViewModel(private val getBestPodcastsUseCase: GetBestPodcastsUseCase) : LoadMoreVM<Podcast>() {

    init {
        loadMore()
    }

    override var startOffsetPage: Int = 1

    override suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean): Result<List<Podcast>> {
        return getBestPodcastsUseCase(GetBestPodcastParam(currentPage, "vn"))
    }
}