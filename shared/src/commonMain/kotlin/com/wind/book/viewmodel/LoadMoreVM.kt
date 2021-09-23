package com.wind.book.viewmodel

import com.wind.book.log
import com.wind.book.viewmodel.util.Constant
import com.wind.book.viewmodel.util.LoadState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class LoadMoreVM<T> : BaseViewModel() {

    private var canNotLoad: Boolean = false
    private val _loadState: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Loading(isEmpty = true))
    private val _refreshState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    // public for listening load state
    val loadState: Flow<LoadState> = _loadState
    val refreshState: StateFlow<Boolean> = _refreshState

    // child class can override
    protected open var startOffsetPage = Constant.START_OFFSET_PAGE
    protected open var pageSize = Constant.PAGE_SIZE
    private val _data: MutableStateFlow<List<T>> = MutableStateFlow(emptyList())

    private var currentPage = startOffsetPage
    private val cachedData: List<T>
        get() {
            return runBlocking { _data.first() }
        }

    val data: StateFlow<List<T>> = _data

    private val _scrollToTop: MutableSharedFlow<Unit> = MutableSharedFlow()
    val scrollToTop: SharedFlow<Unit> = _scrollToTop

    private val currentLoadState: LoadState
        get() {
            return runBlocking { _loadState.first() }
        }

    private val loadAPIScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        // Because we have a case when we done loading data, but still support add/remove data realtime
        // trigger this method to update the empty state of UI
        clientScope.launch {
            _data.collectLatest {
                updateDataState(it.isEmpty())
            }
        }
    }

    override fun onCleared() {
        loadAPIScope.cancel()
        super.onCleared()
    }

    fun loadMore(isRefresh: Boolean = false) {
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
                    _data.value = cachedData
                    // increase the size to get the next page
                    currentPage += pageSize
                    val endOfPage = notDuplicatedData.isEmpty()
                    onSuccess(isEmpty = cachedData.isEmpty(), endOfPage = endOfPage)

                    // auto load more if the data size < VISIBLE_THRESHOLD and we are not at the end of page
                    if (!endOfPage && cachedData.size < Constant.VISIBLE_THRESHOLD) {
                        log.v { "Auto load more because the data size is less than VISIBLE_THRESHOLD" }
                        loadMore()
                    }
                }
                .onFailure {
                    ensureActive()
                    onError(it, cachedData.isEmpty())
                }
        }
    }

    fun retry() {
        log.v { "retry" }
        onRetry()
        loadMore()
    }

    fun refresh() {
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
    fun scrollToTop() {
        log.v { "scrollToTop" }
        clientScope.launch {
            delay(300)
            _scrollToTop.emit(Unit)
        }
    }

    abstract suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean): Result<List<T>>

    private fun canLoad(): Boolean {
        return !canNotLoad
    }

    private fun onLoading(isEmpty: Boolean) {
        log.v { "onLoading isEmpty $isEmpty" }
        _loadState.value = LoadState.Loading(isEmpty)
        canNotLoad = true
    }

    private fun onSuccess(isEmpty: Boolean, endOfPage: Boolean) {
        log.v { "onSuccessxxx isEmpty $isEmpty endOfPage $endOfPage" }
        if (endOfPage) {
            canNotLoad = true
            _loadState.value = LoadState.NotLoading.Complete(isEmpty)
        } else {
            _loadState.value = LoadState.Loading(isEmpty)
            canNotLoad = false
        }
        _refreshState.value = false
    }

    private fun onError(exception: Throwable, isEmpty: Boolean) {
        log.v { "onError isEmpty $isEmpty ${exception.stackTraceToString()}" }
        canNotLoad = true
        _loadState.value = LoadState.Error(exception, isEmpty)
        _refreshState.value = false
        clientScope.launch {
            toastError.emit(exception)
        }
    }

    private fun onRefresh() {
        log.v { "onRefresh" }
        canNotLoad = false
        _refreshState.value = true
    }

    private fun onRetry() {
        log.v { "onRetry" }
        canNotLoad = false
    }

    /**
     * Data may change, trigger the previous load state
     */
    protected fun updateDataState(isEmpty: Boolean) {
        val loadState = getLoadState()
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
            null -> null
        }?.let {
            log.v { "updateStateIfCompleted current state $it" }
            _loadState.value = it
        }
    }

    private fun getLoadState() = runBlocking {
        _loadState.firstOrNull()
    }
}
