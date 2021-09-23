package com.wind.book.viewmodel.podcast

import com.wind.book.domain.usecase.podcast.GetPodcastParam
import com.wind.book.domain.usecase.podcast.GetPodcastsUseCase
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.LoadMoreVM

class PodcastsViewModel(private val getPodcastsUseCase: GetPodcastsUseCase) : LoadMoreVM<Podcast>() {

    init {
        loadMore()
    }

    override var startOffsetPage: Int = 1

    override suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean): Result<List<Podcast>> {
        return getPodcastsUseCase(GetPodcastParam(currentPage, "vn"))
    }
}
