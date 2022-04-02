package com.wind.book.viewmodel.podcast_detail

import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailParam
import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailUseCase
import com.wind.book.model.Episode
import com.wind.book.model.Podcast
import com.wind.book.model.SortMode
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.launch

interface PodcastDetailEvent : LoadMoreEvent {
    fun setArgs(podcast: Podcast)
    fun onShare(podcast: Podcast)
    fun onOpenLink(podcast: Podcast)
}

sealed class PodcastDetailEffect : BaseEffect() {
    data class Share(val podcast: Podcast) : PodcastDetailEffect()
    data class OpenLink(val podcast: Podcast) : PodcastDetailEffect()
}

class PodcastDetailViewModel(
    private val getPodcastDetailUseCase: GetPodcastDetailUseCase
) : LoadMoreVM<Episode, PodcastDetailEffect>(), PodcastDetailEvent {

    override var pageSize = 10
    override val event = this as PodcastDetailEvent

    private var sortMode = SortMode.RECENT_FIRST.name
    private var podcast: Podcast? = null

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Episode>> {
        val podcast = podcast ?: return Result.failure(NullPointerException())
        val param = GetPodcastDetailParam(
            podcast.id,
            podcast.nextEpisodePubDate,
            sortMode
        )
        return getPodcastDetailUseCase(param)
            .onSuccess {
                this@PodcastDetailViewModel.podcast = it
            }
            .map { it.episodes }
    }

    override fun setArgs(podcast: Podcast) {
        this.podcast = podcast
        loadData()
    }

    override fun onShare(podcast: Podcast) {
        clientScope.launch { _effect.emit(PodcastDetailEffect.Share(podcast)) }
    }

    override fun onOpenLink(podcast: Podcast) {
        clientScope.launch { _effect.emit(PodcastDetailEffect.OpenLink(podcast)) }
    }
}
