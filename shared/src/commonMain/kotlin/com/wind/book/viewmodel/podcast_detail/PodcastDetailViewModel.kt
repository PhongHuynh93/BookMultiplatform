package com.wind.book.viewmodel.podcast_detail

import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailParam
import com.wind.book.domain.usecase.podcast_detail.GetPodcastDetailUseCase
import com.wind.book.model.Episode
import com.wind.book.model.Podcast
import com.wind.book.model.SortMode
import com.wind.book.viewmodel.*
import com.wind.book.viewmodel.model.PodcastNav
import com.wind.book.viewmodel.util.LoadState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PodcastDetailState(
    val loadState: LoadState = LoadState.Loading(isEmpty = true),
    val refreshState: Boolean = false,
    val podcast: Podcast? = null,
    val data: List<Episode> = emptyList(),
) : BaseState() {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        loadState = LoadState.Loading(isEmpty = true),
        refreshState = false,
        podcast = null,
        data = emptyList(),
    )
}

fun MutableStateFlow<PodcastDetailState>.update(
    loadState: LoadState = value.loadState,
    refreshState: Boolean = value.refreshState,
    podcast: Podcast? = value.podcast,
    data: List<Episode> = value.data,
) {
    value = value.copy(
        loadState = loadState,
        refreshState = refreshState,
        podcast = podcast,
        data = data
    )
}

interface PodcastDetailEvent : LoadMoreEvent {
    fun setArgs(podcastNav: PodcastNav)
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

    private val _podcastDetailState = MutableStateFlow(PodcastDetailState())
    val podcastDetailState = _podcastDetailState

    private val _podcastDetailEffect = MutableSharedFlow<PodcastDetailEffect>()
    val podcastDetailEffect = _podcastDetailEffect.asSharedFlow()

    override var pageSize = 10
    override val event = this as PodcastDetailEvent

    private var sortMode = SortMode.RECENT_FIRST.name
    private var podcast: Podcast? = null

    init {

        clientScope.launch {
            state.collect {
                _podcastDetailState.emit(
                    PodcastDetailState(
                        it.loadState,
                        it.refreshState,
                        podcast,
                        it.data
                    )
                )
            }
        }
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

    override fun setArgs(podcastNav: PodcastNav) {
        clientScope.launch {
            podcast = Podcast(
                id = podcastNav.id,
                title = podcastNav.title,
                publisher = podcastNav.publisher,
                image = podcastNav.image,
                thumbnail = podcastNav.thumbnail,
                description = podcastNav.description,
                totalEpisodes = podcastNav.totalEpisodes
            )
            loadMore(true)
        }
    }

    override fun onShare(podcast: Podcast) {
        clientScope.launch { _podcastDetailEffect.emit(PodcastDetailEffect.Share(podcast)) }
    }

    override fun onOpenLink(podcast: Podcast) {
        clientScope.launch { _podcastDetailEffect.emit(PodcastDetailEffect.OpenLink(podcast)) }
    }
}
