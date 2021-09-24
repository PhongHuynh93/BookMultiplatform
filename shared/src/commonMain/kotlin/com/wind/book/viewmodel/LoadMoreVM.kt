package com.wind.book.viewmodel

import com.wind.book.log
import com.wind.book.viewmodel.util.Constant
import com.wind.book.viewmodel.util.LoadState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class LoadMoreState<T>(
    val loadState: LoadState = LoadState.Loading(isEmpty = true),
    val refreshState: Boolean = false,
    val data: List<T> = emptyList(),
) : BaseState() {

    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        loadState = LoadState.Loading(isEmpty = true),
        refreshState = false,
        data = emptyList(),
    )
}

fun <T> MutableStateFlow<LoadMoreState<T>>.update(
    loadState: LoadState = value.loadState,
    refreshState: Boolean = value.refreshState,
    data: List<T> = value.data,
) {
    value = value.copy(
        loadState = loadState,
        refreshState = refreshState,
        data = data
    )
}

interface LoadMoreEvent : BaseEvent {
    fun loadMore(isRefresh: Boolean = false)
    fun retry()
    fun refresh()
    fun scrollToTop()
}

sealed class LoadMoreEffect : BaseEffect() {
    object ScrollToTop : LoadMoreEffect()
}

abstract class LoadMoreVM<T> : BaseMVIViewModel(), LoadMoreEvent {
    // region MVI
    private val _state = MutableStateFlow(LoadMoreState<T>())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoadMoreEffect>()
    override val effect = _effect.asSharedFlow()

    override val event = this as LoadMoreEvent
    // end region

    private var canNotLoad: Boolean = false

    // child class can override
    protected open var startOffsetPage = Constant.START_OFFSET_PAGE
    protected open var pageSize = Constant.PAGE_SIZE

    private var currentPage = startOffsetPage
    private val cachedData: List<T>
        get() {
            return runBlocking { state.first().data }
        }

    private val currentLoadState: LoadState
        get() {
            return runBlocking { state.first().loadState }
        }

    private val loadAPIScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        // Because we have a case when we done loading data, but still support add/remove data realtime
        // trigger this method to update the empty state of UI
        clientScope.launch {
            state.collectLatest {
                updateDataState(it.data.isEmpty())
            }
        }
    }

    override fun onCleared() {
        loadAPIScope.cancel()
        super.onCleared()
    }

    override fun loadMore(isRefresh: Boolean) {
        loadAPIScope.launch {
            if (!canLoad()) {
                return@launch
            }
            if (isRefresh) {
                onLoading(cachedData.isEmpty())
                currentPage = startOffsetPage
            } else {
                onLoading(cachedData.isEmpty())
            }
            log.v { "start load more $currentPage isRefresh $isRefresh" }
            apiCall(currentPage, pageSize, isRefresh)
                .onSuccess { list ->
                    ensureActive()
                    log.v { "return data current page $currentPage isRefresh $isRefresh data ${list.size}" }

                    val cachedData = if (isRefresh) {
                        scrollToTop()
                        mutableListOf()
                    } else {
                        this@LoadMoreVM.cachedData.toMutableList()
                    }

                    // filter duplication
                    val notDuplicatedData = list.filterNot {
                        cachedData.contains(it)
                    }
                    cachedData.addAll(notDuplicatedData)
                    log.v { "notDuplicatedData current page $currentPage isRefresh $isRefresh notDuplicatedData ${notDuplicatedData.size}" }
                    log.v { "cachedComment current page $currentPage isRefresh $isRefresh cachedData ${cachedData.size}" }
                    _state.update(
                        data = cachedData
                    )
                    // increase the size to get the next page
                    currentPage += pageSize
                    val endOfPage = notDuplicatedData.isEmpty()
                    onSuccess(isEmpty = cachedData.isEmpty(), endOfPage = endOfPage)

                    // auto load more if the data size < VISIBLE_THRESHOLD and we are not at the end of page
                    if (!endOfPage && cachedData.size < Constant.VISIBLE_THRESHOLD) {
                        log.v { "Auto load more because the data size is less than VISIBLE_THRESHOLD" }
                        loadMore(false)
                    }
                }
                .onFailure {
                    ensureActive()
                    onError(it, cachedData.isEmpty())
                }
        }
    }

    override fun retry() {
        log.v { "retry" }
        onRetry()
        loadMore(false)
    }

    override fun refresh() {
        // cancel all the previous APIs
        log.v { "Refresh" }
        loadAPIScope.coroutineContext.cancelChildren()
        onRefresh()
        loadMore(isRefresh = true)
    }

    /**
     * delay a little bit before scrolling to top
     * because need to wait the list finish calculating the diffutil
     */
    override fun scrollToTop() {
        log.v { "scrollToTop" }
        clientScope.launch {
            delay(300)
            _effect.emit(LoadMoreEffect.ScrollToTop)
        }
    }

    abstract suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<T>>

    private fun canLoad(): Boolean {
        return !canNotLoad
    }

    private fun onLoading(isEmpty: Boolean) {
        log.v { "onLoading isEmpty $isEmpty" }
        _state.update(
            loadState = LoadState.Loading(isEmpty)
        )
        canNotLoad = true
    }

    private fun onSuccess(isEmpty: Boolean, endOfPage: Boolean) {
        log.v { "onSuccessxxx isEmpty $isEmpty endOfPage $endOfPage" }
        val loadState = if (endOfPage) {
            canNotLoad = true
            LoadState.NotLoading.Complete(isEmpty)
        } else {
            canNotLoad = false
            LoadState.Loading(isEmpty)
        }
        _state.update(
            loadState = loadState,
            refreshState = false
        )
    }

    private fun onError(exception: Throwable, isEmpty: Boolean) {
        log.v { "onError isEmpty $isEmpty ${exception.stackTraceToString()}" }
        canNotLoad = true
        _state.update(
            loadState = LoadState.Error(exception, isEmpty),
            refreshState = false
        )
        clientScope.launch {
            toastError.emit(exception)
        }
    }

    private fun onRefresh() {
        log.v { "onRefresh" }
        canNotLoad = false
        _state.update(refreshState = true)
    }

    private fun onRetry() {
        log.v { "onRetry" }
        canNotLoad = false
    }

    /**
     * Data may change, trigger the previous load state
     */
    protected fun updateDataState(isEmpty: Boolean) {
        val loadState = currentLoadState
        log.v { "updateStateIfCompleted previous state $loadState" }
        when (loadState) {
            is LoadState.Error -> LoadState.Error(loadState.error, isEmpty)
            is LoadState.Loading -> LoadState.Loading(isEmpty)
            is LoadState.NotLoading -> {
                if (isEmpty) {
                    LoadState.NotLoading.Complete(isEmpty)
                } else {
                    LoadState.NotLoading.Incomplete(isEmpty)
                }
            }
        }.let {
            log.v { "updateStateIfCompleted current state $it" }
            _state.update(
                loadState = it
            )
        }
    }
}
