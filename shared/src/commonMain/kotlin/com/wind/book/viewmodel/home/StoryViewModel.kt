package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.story.GetArticleListParam
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import com.wind.book.model.Article
import com.wind.book.viewmodel.BaseViewModel
import com.wind.book.viewmodel.util.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoryViewModel(
    private val getArticleListUseCase: GetArticleListUseCase
) : BaseViewModel() {
    private val _loadState: MutableStateFlow<LoadState> = MutableStateFlow(LoadState.Loading(isEmpty = true))

    // public for listening load state
    val loadState: Flow<LoadState> = _loadState

    private val _data: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
    val data: StateFlow<List<Article>> = _data

    init {
        loadData()
    }

    fun retry() {
        _loadState.value = LoadState.Loading(true)
        loadData()
    }

    fun loadData() {
        clientScope.launch {
            apiCall(1).onSuccess {
                _loadState.value = LoadState.NotLoading.Complete(it.isEmpty())
                _data.value = it
            }.onFailure {
                _loadState.value = LoadState.Error(it, true)
                _data.value = emptyList()
            }
        }
    }

    suspend fun apiCall(currentPage: Int): Result<List<Article>> {
        return getArticleListUseCase(GetArticleListParam(currentPage = currentPage, section = "home"))
    }
}
