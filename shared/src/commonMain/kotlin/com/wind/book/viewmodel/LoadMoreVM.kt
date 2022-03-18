package com.wind.book.viewmodel

import com.wind.book.log
import com.wind.book.model.Identifiable
import com.wind.book.model.PlatformType
import com.wind.book.observeConnectionState
import com.wind.book.platform
import com.wind.book.toastError
import com.wind.book.util.ConnectionState
import com.wind.book.viewmodel.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
    fun loadMore()
    fun retry()
    fun refresh()
    fun loadMore(indexOfItem: Int)
}

private val TAG = LoadMoreVM::class.simpleName

abstract class LoadMoreVM<T : Identifiable, E : BaseEffect> : BaseMVIViewModel(), LoadMoreEvent {
    // region MVI
    private val _state = MutableStateFlow(LoadingState())
    override val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
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

    init {
        clientScope.launch {
            observeConnectionState.observeConnectivityAsFlow().collectLatest {
                when (it) {
                    ConnectionState.ConnectionAvailable -> {
                        val currentScreen = _state.value.screen
                        if (currentScreen is LoadingScreen.Error || (currentScreen is LoadingScreen.Data<*> && currentScreen.errorMessage != null)) {
                            retry()
                        }
                    }
                    ConnectionState.NoConnection -> {}
                }
            }
        }
    }

    override fun onCleared() {
        // on iOS
        // don't use `cancel` but just `cancelChildren`
        // because the view model is reused later
        // If use `cancel`, the scope can not be used at that time
        if (platform == PlatformType.IOS) {
            loadAPIScope.coroutineContext.cancelChildren()
        } else {
            loadAPIScope.cancel()
        }
        super.onCleared()
    }

    override fun loadMore() {
        loadMore(isRefresh = false)
    }

    private fun loadMore(isRefresh: Boolean) {
        log.v { "$TAG loadMore $isRefresh" }
        loadAPIScope.launch {
            if (!canLoad()) {
                return@launch
            }
            onLoading(isRefresh)

            val currentPage = if (isRefresh) {
                startOffsetPage
            } else {
                currentPage ?: startOffsetPage
            }
            log.v { "$TAG start load more $currentPage" }
            apiCall(currentPage, pageSize, isRefresh)
                .onSuccess { list ->
                    ensureActive()
                    log.v { "$TAG return data current page=$currentPage dataSize=${list.size}" }

                    val cachedData = if (isRefresh) {
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
                    log.v { "$TAG notDuplicatedData notDuplicatedData=${notDuplicatedData.size}" }
                    log.v { "$TAG cachedComment cachedData=${cachedData.size}" }
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

    override fun loadMore(indexOfItem: Int) {
        val screen = _state.value.screen
        if (screen is LoadingScreen.Data<*>) {
            val list = screen.data
            if (list.isEmpty()) {
                return
            }
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
        log.v { "$TAG canLoad ${!canNotLoad}" }
        return !canNotLoad
    }

    private fun onLoading(isRefresh: Boolean) {
        log.v { "$TAG onLoading" }
        canNotLoad = true
        //  we only show loading screen when we don't have data
        val screen = _state.value.screen
        if (screen is LoadingScreen.Data<*>) {
            _state.update(
                screen = screen.copy(
                    isRefresh = isRefresh
                )
            )
        } else {
            _state.update(LoadingScreen.Loading)
        }
    }

    private fun onSuccess(endOfPage: Boolean) {
        canNotLoad = endOfPage
        log.v { "$TAG onSuccess with endOfPage=$endOfPage" }
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
    }

    private fun onRetry() {
        log.v { "$TAG onRetry" }
        canNotLoad = false
    }

    private fun calcNextPage(currentPage: Int) = currentPage + pageSize
}
