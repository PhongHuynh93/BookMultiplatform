package com.wind.book.data.repository.article

import com.wind.book.data.mapping.ArticleMapper
import com.wind.book.data.model.dto.SectionResDto
import com.wind.book.data.util.Constant
import com.wind.book.model.Article
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom

interface StoryAPI {
    suspend fun get(section: String): List<Article>
}

internal class StoryAPIImpl(private val client: HttpClient) : StoryAPI {
    private val articleMapper = ArticleMapper()

    override suspend fun get(section: String): List<Article> {
        return client.get<SectionResDto> {
            url {
                takeFrom(Constant.Book.HOST)
                path(Constant.Book.STORY_PATH, "$section.json")
                parameter(Constant.Book.QUERY_API_KEY, Constant.Book.API_KEY)
            }
        }.results?.mapNotNull {
            it?.let {
                articleMapper.apply(it)
            }
        } ?: emptyList()
    }
}
