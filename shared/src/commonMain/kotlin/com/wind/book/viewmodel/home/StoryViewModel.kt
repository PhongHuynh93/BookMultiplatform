package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.story.GetArticleListParam
import com.wind.book.domain.usecase.story.GetArticleListUseCase
import com.wind.book.model.Article
import com.wind.book.viewmodel.LoadingVM

class StoryViewModel(
    private val getArticleListUseCase: GetArticleListUseCase
) : LoadingVM<Article>() {

    override suspend fun apiCall(): Result<List<Article>> {
        return getArticleListUseCase(GetArticleListParam(section = "home"))
    }
}
