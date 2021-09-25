package com.wind.book.viewmodel.iab

import com.wind.book.log
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.BaseEvent
import com.wind.book.viewmodel.BaseMVIViewModel
import com.wind.book.viewmodel.BaseState
import com.wind.book.viewmodel.model.IABNav
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

data class IABState(
    val url: String? = null,
    val title: String = "",
    val loadDone: Boolean = false,
    val progress: Int = 0
) : BaseState()

fun MutableStateFlow<IABState>.update(
    url: String? = value.url,
    title: String = value.title,
    loadDone: Boolean = value.loadDone,
    progress: Int = value.progress
) {
    value = value.copy(
        url = url,
        title = title,
        loadDone = loadDone,
        progress = progress
    )
}

interface IABEvent : BaseEvent {
    fun onProgressChanged(progress: Int)
    fun onPageFinished()
    fun onReceivedError()
    fun setIABNav(iabNav: IABNav)
}

sealed class IABEffect : BaseEffect()

class IABViewModel : BaseMVIViewModel(), IABEvent {
    private val _state = MutableStateFlow(IABState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<IABEffect>()
    override val effect = _effect.asSharedFlow()

    override val event = this as IABEvent

    override fun setIABNav(iabNav: IABNav) {
        _state.update(
            url = iabNav.url,
            title = iabNav.title
        )
    }

    override fun onProgressChanged(progress: Int) {
        log.e { "progress $progress" }
        if (progress >= 30) {
            _state.update(
                progress = progress,
                loadDone = true
            )
        } else {
            _state.update(
                progress = progress,
            )
        }
    }

    override fun onPageFinished() {
        _state.update(
            loadDone = true
        )
    }

    override fun onReceivedError() {
        _state.update(
            loadDone = true
        )
    }
}
