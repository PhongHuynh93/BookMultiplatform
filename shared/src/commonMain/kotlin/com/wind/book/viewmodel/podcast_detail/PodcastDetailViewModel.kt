package com.wind.book.viewmodel.podcast_detail

import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailParam
import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailUseCase
import com.wind.book.model.Episode
import com.wind.book.model.Podcast
import com.wind.book.model.SortMode
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface PodcastDetailEvent : LoadMoreEvent {
    fun setArgs(podcast: Podcast)
    fun onShare(podcast: Podcast)
    fun onOpenLink(podcast: Podcast)
}

sealed class PodcastDetailEffect : BaseEffect() {
    class LMEffect(val loadMoreEffect: LoadMoreEffect) : PodcastDetailEffect()
    data class Share(val podcast: Podcast) : PodcastDetailEffect()
    data class OpenLink(val podcast: Podcast) : PodcastDetailEffect()
}

class PodcastDetailViewModel(
    private val getPodcastDetailUseCase: GetPodcastDetailUseCase
) : LoadMoreVM<Episode>(), PodcastDetailEvent {

    private val _podcastDetailEffect = MutableSharedFlow<PodcastDetailEffect>()
    val podcastDetailEffect = _podcastDetailEffect.asSharedFlow()

    override var pageSize = 10
    override val event = this as PodcastDetailEvent

    private var sortMode = SortMode.RECENT_FIRST.name
    private var podcast: Podcast? = null

    init {
        // capture the base effect and emit again
        clientScope.launch {
            effect.collectLatest {
                _podcastDetailEffect.emit(PodcastDetailEffect.LMEffect(it))
            }
        }
    }

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
        loadMore()
    }

    override fun onShare(podcast: Podcast) {
        clientScope.launch { _podcastDetailEffect.emit(PodcastDetailEffect.Share(podcast)) }
    }

    override fun onOpenLink(podcast: Podcast) {
        clientScope.launch { _podcastDetailEffect.emit(PodcastDetailEffect.OpenLink(podcast)) }
    }
}
