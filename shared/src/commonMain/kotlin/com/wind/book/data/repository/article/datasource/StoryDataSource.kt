package com.wind.book.data.repository.article.datasource

import com.wind.book.data.repository.article.StoryAPI
import com.wind.book.model.Article

interface RemoteStoryDataSource {
    suspend fun getArticleList(section: String): List<Article>
}

internal class RemoteStoryDataSourceImpl(private val storyAPI: StoryAPI) : RemoteStoryDataSource {
    override suspend fun getArticleList(section: String): List<Article> {
        return storyAPI.get(section)
    }
}
