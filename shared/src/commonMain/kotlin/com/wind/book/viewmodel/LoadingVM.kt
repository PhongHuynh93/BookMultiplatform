package com.wind.book.viewmodel

import com.wind.book.model.Identifiable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoadingEffect : BaseEffect()

interface LoadingEvent : BaseEvent {
    fun retry()
    fun refresh()
    fun loadData(isRefresh: Boolean = false)
}

abstract class LoadingVM<T : Identifiable> : BaseMVIViewModel(), LoadingEvent {
    private val _state = MutableStateFlow(LoadingState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoadingEffect>()
    override val effect = _effect.asSharedFlow()

    override val event: LoadingEvent
        get() = this

    init {
        // init of base class is called before the injection of child class
        // which lead to crash when calling loadData()
        // so delay a little bit
        clientScope.launch {
            delay(50)
            loadData()
        }
    }

    override fun retry() {
        loadData()
    }

    override fun refresh() {
        loadData(isRefresh = true)
    }

    override fun loadData(isRefresh: Boolean) {
        clientScope.launch {
            if (isRefresh) {
                _state.update(LoadingScreen.Loading)
            }
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

    abstract suspend fun apiCall(): Result<List<T>>
}
