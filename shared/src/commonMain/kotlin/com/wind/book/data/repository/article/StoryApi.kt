package com.wind.book.data.repository.article

import com.wind.book.data.mapping.ArticleMapper
import com.wind.book.data.model.dto.SectionResDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.data.util.Constant
import com.wind.book.model.Article
import io.ktor.client.request.parameter

interface StoryAPI {
    suspend fun get(section: String): List<Article>
}

internal class StoryAPIImpl : BaseAPI(), StoryAPI {
    private val articleMapper = ArticleMapper()

    override val baseUrl: String
        get() = Constant.Book.HOST

    override suspend fun get(section: String): List<Article> {
        return doGet<SectionResDto> {
            apiPath(Constant.Book.STORY_PATH, "$section.json")
            parameter(Constant.Book.QUERY_API_KEY, Constant.Book.API_KEY)
        }.getOrThrow().results?.mapNotNull {
            it?.let {
                articleMapper.apply(it)
            }
        } ?: emptyList()
    }
}
