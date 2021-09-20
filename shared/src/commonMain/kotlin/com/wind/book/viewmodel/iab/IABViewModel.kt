package com.wind.book.viewmodel.iab

import com.wind.book.log
import com.wind.book.share.WhileViewSubscribed
import com.wind.book.viewmodel.BaseViewModel
import com.wind.book.viewmodel.model.IABNav
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IABViewModel : BaseViewModel() {
    private val _iabNav: MutableSharedFlow<IABNav> = MutableSharedFlow(replay = 1)
    val iabNav: SharedFlow<IABNav> = _iabNav
    val link = iabNav.map {
        it.url
    }
    val title = iabNav.map {
        it.title
    }.stateIn(
        scope = clientScope,
        started = WhileViewSubscribed,
        initialValue = ""
    )

    private val _loadDone: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadDone: StateFlow<Boolean> = _loadDone
    private val _progress: MutableStateFlow<Int> = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress

    fun setIABNav(iabNav: IABNav) {
        clientScope.launch {
            _iabNav.emit(iabNav)
        }
    }

    fun onProgressChanged(progress: Int) {
        log.e { "progress $progress" }
        _progress.value = progress
        if (progress >= 30) {
            _loadDone.value = true
        }
    }

    fun onPageFinished() {
        _loadDone.value = true
    }

    fun onReceivedError() {
        _loadDone.value = true
    }
}
