package com.wind.book.data.repository.article

import com.wind.book.data.repository.article.datasource.RemoteStoryDataSource
import com.wind.book.model.Article

interface StoryRepository {
    suspend fun getArticleList(section: String): List<Article>
}

internal class StoryRepositoryImpl(
    private val remoteSource: RemoteStoryDataSource
) : StoryRepository {
    override suspend fun getArticleList(section: String): List<Article> {
        return remoteSource.getArticleList(section)
    }
}
