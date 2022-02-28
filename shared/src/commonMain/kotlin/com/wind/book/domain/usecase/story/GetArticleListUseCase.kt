package com.wind.book.domain.usecase.story

import com.wind.book.data.repository.article.StoryRepository
import com.wind.book.domain.usecase.UseCase
import com.wind.book.model.Article
import com.wind.book.platformCoroutineDispatcher

class GetArticleListParam(val section: String)
class GetArticleListUseCase constructor(
    private val storyRepository: StoryRepository
) : UseCase<GetArticleListParam, List<Article>>(platformCoroutineDispatcher) {
    override suspend fun execute(parameters: GetArticleListParam): List<Article> {
        return storyRepository.getArticleList(parameters.section)
    }
}
