package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.story.GetArticleListParam
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import com.wind.book.model.Article
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadingVM
import kotlinx.coroutines.launch

class StoryViewModel(
    private val getArticleListUseCase: GetArticleListUseCase
) : LoadingVM<Article, BaseEffect>() {

    init {
        clientScope.launch {
            loadData()
        }
    }

    override suspend fun apiCall(): Result<List<Article>> {
        return getArticleListUseCase(GetArticleListParam(section = "home"))
    }
}
