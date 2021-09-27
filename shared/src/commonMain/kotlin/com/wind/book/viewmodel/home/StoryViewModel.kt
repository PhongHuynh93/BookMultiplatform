package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.story.GetArticleListParam
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import com.wind.book.model.Article
import com.wind.book.viewmodel.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class StoryEffect : BaseEffect()

interface StoryEvent : BaseEvent {
    fun retry()
    fun refresh()
}

class StoryViewModel(
    private val getArticleListUseCase: GetArticleListUseCase
) : BaseMVIViewModel(), StoryEvent {

    private val _state = MutableStateFlow(LoadingState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<StoryEffect>()
    override val effect = _effect.asSharedFlow()

    override val event: StoryEvent
        get() = this

    init {
        loadData()
    }

    override fun retry() {
        loadData()
    }

    override fun refresh() {
        loadData()
    }

    private fun loadData() {
        clientScope.launch {
            _state.update(LoadingScreen.Loading)
            apiCall().onSuccess {
                if (it.isEmpty()) {
                    // TODO: 26/09/2021 handle localization
                    _state.update(screen = LoadingScreen.NoData("No Data"))
                } else {
                    _state.update(
                        screen = LoadingScreen.Data(
                            data = it,
                            isEndPage = true,
                        )
                    )
                }
            }.onFailure {
                _state.update(
                    screen = LoadingScreen.Error(it.message.orEmpty())
                )
            }
        }
    }

    private suspend fun apiCall(): Result<List<Article>> {
        return getArticleListUseCase(GetArticleListParam(section = "home"))
    }
}
