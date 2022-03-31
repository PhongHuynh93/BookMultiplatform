package com.wind.book.viewmodel

import com.wind.book.model.Identifiable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface LoadingEvent : BaseEvent {
    fun retry()
    fun refresh()
    fun loadData()
}

abstract class LoadingVM<T : Identifiable, E : BaseEffect> : BaseMVIViewModel(), LoadingEvent {
    private val _state = MutableStateFlow(LoadingState())
    override val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    override val effect = _effect.asSharedFlow()

    override val event: LoadingEvent
        get() = this

    override fun retry() {
        loadData()
    }

    override fun refresh() {
        loadData(isRefresh = true)
    }

    override fun loadData() {
        loadData(isRefresh = false)
    }

    private fun loadData(isRefresh: Boolean) {
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
