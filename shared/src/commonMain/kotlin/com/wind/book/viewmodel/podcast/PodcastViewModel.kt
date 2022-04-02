package com.wind.book.viewmodel.podcast

import com.wind.book.domain.usecase.podcast.GetPodcastListParam
import com.wind.book.domain.usecase.podcast.GetPodcastUseCase
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.launch

interface PodcastEvent : LoadMoreEvent {
    fun onClick(podcast: Podcast)
}

sealed class PodcastEffect : BaseEffect() {
    data class NavToDetail(val podcast: Podcast) : PodcastEffect()
}

class PodcastViewModel(private val getPodcastUseCase: GetPodcastUseCase) :
    LoadMoreVM<Podcast, PodcastEffect>(), PodcastEvent {

    override val event = this as PodcastEvent

    override var startOffsetPage: Int = 1
    override var pageSize: Int = 1

    init {
        loadData()
    }

    override suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean) =
        getPodcastUseCase(GetPodcastListParam(currentPage, "vn"))

    override fun onClick(podcast: Podcast) {
        clientScope.launch {
            _effect.emit(
                PodcastEffect.NavToDetail(podcast)
            )
        }
    }
}
