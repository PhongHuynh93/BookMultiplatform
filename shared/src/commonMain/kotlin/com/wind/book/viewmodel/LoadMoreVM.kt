package com.wind.book.viewmodel

import com.wind.book.log
import com.wind.book.model.Identifiable
import com.wind.book.toastError
import com.wind.book.viewmodel.util.Constant
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class LoadingScreen {
    data class Data<T>(
        val data: List<T> = emptyList(),
        val isRefresh: Boolean = false,
        val isEndPage: Boolean = false,
        val errorMessage: String? = null
    ) : LoadingScreen() {
        override fun toString(): String {
            return "data=${data.size} isRefresh=$isRefresh isEndPage=$isEndPage errorMessage=$errorMessage"
        }
    }

    object Loading : LoadingScreen()
    data class Error(val errorMessage: String) : LoadingScreen()
    data class NoData(val message: String) : LoadingScreen()
}

data class LoadingState(
    val screen: LoadingScreen,
) : BaseState() {

    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        LoadingScreen.Loading
    )
}

fun MutableStateFlow<LoadingState>.update(
    screen: LoadingScreen,
) {
    value = value.copy(
        screen = screen,
    )
}

interface LoadMoreEvent : BaseEvent {
    fun loadMore(isRefresh: Boolean = false)
    fun retry()
    fun refresh()
    fun scrollToTop()
    fun loadMore(indexOfItem: Int)
}

sealed class LoadMoreEffect : BaseEffect() {
    object ScrollToTop : LoadMoreEffect()
}

private val TAG = LoadMoreVM::class.simpleName

abstract class LoadMoreVM<T : Identifiable> : BaseMVIViewModel(), LoadMoreEvent {
    // region MVI
    private val _state = MutableStateFlow(LoadingState())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoadMoreEffect>()
    override val effect = _effect.asSharedFlow()

    override val event = this as LoadMoreEvent
    // end region

    private var canNotLoad: Boolean = false

    // child class can override
    protected open var startOffsetPage = Constant.START_OFFSET_PAGE
    protected open var pageSize = Constant.PAGE_SIZE
    protected open var visibleThreshold = Constant.VISIBLE_THRESHOLD

    private var currentPage: Int? = null

    @Suppress("UNCHECKED_CAST")
    private val cachedData: List<T>
        get() {
            return runBlocking {
                when (val screen = state.first().screen) {
                    is LoadingScreen.Data<*> -> {
                        screen.data as List<T>
                    }
                    else -> emptyList()
                }
            }
        }

    private val loadAPIScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCleared() {
        loadAPIScope.cancel()
        super.onCleared()
    }

    override fun loadMore(isRefresh: Boolean) {
        loadAPIScope.launch {
            if (!canLoad()) {
                return@launch
            }
            onLoading()

            val currentPage = if (isRefresh) {
                startOffsetPage
            } else {
                currentPage ?: startOffsetPage
            }
            log.v { "$TAG start load more $currentPage isRefresh $isRefresh" }
            apiCall(currentPage, pageSize, isRefresh)
                .onSuccess { list ->
                    ensureActive()
                    log.v { "$TAG return data current page=$currentPage isRefresh=$isRefresh dataSize=${list.size}" }

                    val cachedData = if (isRefresh) {
                        scrollToTop()
                        mutableListOf()
                    } else {
                        this@LoadMoreVM.cachedData.toMutableList()
                    }

                    // filter duplication
                    val notDuplicatedData = list.filterNot {
                        cachedData.any { cached ->
                            it.id == cached.id
                        }
                    }
                    cachedData.addAll(notDuplicatedData)
                    log.v { "$TAG notDuplicatedData current page=$currentPage isRefresh=$isRefresh notDuplicatedData=${notDuplicatedData.size}" }
                    log.v { "$TAG cachedComment current page=$currentPage isRefresh=$isRefresh cachedData=${cachedData.size}" }
                    // increase the size to get the next page
                    this@LoadMoreVM.currentPage = calcNextPage(currentPage)
                    val endOfPage = notDuplicatedData.isEmpty()
                    onSuccess(endOfPage = endOfPage)
                    // FIXME: 26/09/2021 handle text localization here
                    if (cachedData.isEmpty()) {
                        _state.update(screen = LoadingScreen.NoData("No Data"))
                    } else {
                        _state.update(
                            screen = LoadingScreen.Data<T>(
                                data = cachedData,
                                isEndPage = endOfPage,
                                isRefresh = false
                            )
                        )
                    }
                    // auto load more if the data size < VISIBLE_THRESHOLD and we are not at the end of page
                    if (!endOfPage && cachedData.size < Constant.VISIBLE_THRESHOLD) {
                        log.v { "$TAG Auto load more because the data size is less than VISIBLE_THRESHOLD" }
                        loadMore(false)
                    }
                }
                .onFailure {
                    ensureActive()
                    onError(it)
                }
        }
    }

    override fun retry() {
        log.v { "$TAG retry" }
        onRetry()
        loadMore(false)
    }

    override fun refresh() {
        // cancel all the previous APIs
        log.v { "$TAG Refresh" }
        loadAPIScope.coroutineContext.cancelChildren()
        onRefresh()
        loadMore(isRefresh = true)
    }

    /**
     * delay a little bit before scrolling to top
     * because need to wait the list finish calculating the diffutil
     */
    override fun scrollToTop() {
        log.v { "$TAG scrollToTop" }
        clientScope.launch {
            delay(300)
            _effect.emit(LoadMoreEffect.ScrollToTop)
        }
    }

    override fun loadMore(indexOfItem: Int) {
        val screen = _state.value.screen
        if (screen is LoadingScreen.Data<*>) {
            val list = screen.data
            log.d { "$TAG indexOfItem = $indexOfItem" }
            log.d { "$TAG list.size - indexOfItem ${list.size - indexOfItem} visibleThreshold $visibleThreshold" }
            if (list.size - indexOfItem <= visibleThreshold) {
                loadMore(isRefresh = false)
            }
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

    private fun onLoading() {
        log.v { "$TAG onLoading" }
        canNotLoad = true
        if (_state.value.screen !is LoadingScreen.Data<*>) {
            _state.update(LoadingScreen.Loading)
        }
    }

    private fun onSuccess(endOfPage: Boolean) {
        log.v { "$TAG onSuccessxxx" }
        canNotLoad = endOfPage
    }

    private fun onError(exception: Throwable) {
        log.v { "$TAG onError" }
        canNotLoad = true
        when (val screen = state.value.screen) {
            is LoadingScreen.Data<*> -> {
                _state.update(
                    screen = screen.copy(
                        isRefresh = false,
                        errorMessage = exception.message
                    )
                )
            }
            else -> {
                _state.update(
                    screen = LoadingScreen.Error(exception.message.orEmpty())
                )
            }
        }
        clientScope.launch {
            toastError.emit(exception)
        }
    }

    private fun onRefresh() {
        log.v { "$TAG onRefresh" }
        canNotLoad = false
        when (val screen = state.value.screen) {
            is LoadingScreen.Data<*> -> {
                _state.update(
                    screen = screen.copy(
                        isRefresh = true
                    )
                )
            }
            else -> {
                // not care for other screen type
            }
        }
    }

    private fun onRetry() {
        log.v { "$TAG onRetry" }
        canNotLoad = false
    }

    private fun calcNextPage(currentPage: Int) = currentPage + pageSize
}
