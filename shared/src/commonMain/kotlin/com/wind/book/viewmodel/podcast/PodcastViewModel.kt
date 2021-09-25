package com.wind.book.viewmodel.podcast

import com.wind.book.domain.usecase.podcast.GetPodcastListParam
import com.wind.book.domain.usecase.podcast.GetPodcastUseCase
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import com.wind.book.viewmodel.model.PodcastNav
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface PodcastEvent : LoadMoreEvent {
    fun onClick(podcast: Podcast)
}

sealed class PodcastEffect : BaseEffect() {
    class LMEffect(val loadMoreEffect: LoadMoreEffect) : PodcastEffect()
    data class NavToDetail(val podcastNav: PodcastNav) : PodcastEffect()
}

class PodcastViewModel(private val getPodcastUseCase: GetPodcastUseCase) :
    LoadMoreVM<Podcast>(), PodcastEvent {

    private val _podcastEffect = MutableSharedFlow<PodcastEffect>()
    val podcastEffect = _podcastEffect.asSharedFlow()

    override val event = this as PodcastEvent

    override var startOffsetPage: Int = 1

    override suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean) =
        getPodcastUseCase(GetPodcastListParam(currentPage, "vn"))

    override fun calcNextPage(currentPage: Int) = currentPage + 1

    override fun onClick(podcast: Podcast) {
        clientScope.launch {
            _podcastEffect.emit(
                PodcastEffect.NavToDetail(
                    PodcastNav(
                        podcast.id,
                        podcast.title,
                        podcast.publisher,
                        podcast.image,
                        podcast.thumbnail,
                        podcast.description,
                        podcast.totalEpisodes
                    )
                )
            )
        }
    }
}
