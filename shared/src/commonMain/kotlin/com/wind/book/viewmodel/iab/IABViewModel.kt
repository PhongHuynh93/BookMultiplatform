package com.wind.book.viewmodel.iab

import com.wind.book.log
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.BaseEvent
import com.wind.book.viewmodel.BaseMVIViewModel
import com.wind.book.viewmodel.BaseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

data class IABState(
    val loadDone: Boolean = false,
    val progress: Int = 0
) : BaseState() {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        loadDone = false,
        progress = 0
    )
}

fun MutableStateFlow<IABState>.update(
    loadDone: Boolean = value.loadDone,
    progress: Int = value.progress
) {
    value = value.copy(
        loadDone = loadDone,
        progress = progress
    )
}

interface IABEvent : BaseEvent {
    fun onProgressChanged(progress: Int)
    fun onPageFinished()
    fun onReceivedError()
}

sealed class IABEffect : BaseEffect()

class IABViewModel : BaseMVIViewModel(), IABEvent {
    private val _state = MutableStateFlow(IABState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<IABEffect>()
    override val effect = _effect.asSharedFlow()

    override val event = this as IABEvent

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
