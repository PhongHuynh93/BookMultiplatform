package com.wind.book.data.repository.article

import com.wind.book.data.mapping.ArticleMapper
import com.wind.book.data.model.dto.SectionResDto
import com.wind.book.data.util.Constant
import com.wind.book.model.Article
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

interface StoryAPI {
    suspend fun get(section: String): List<Article>
}

internal class StoryAPIImpl(private val client: HttpClient) : StoryAPI {
    private val articleMapper = ArticleMapper()

    override suspend fun get(section: String): List<Article> {
        return client.get<SectionResDto> {
            url {
                takeFrom(Constant.HOST)
                path(Constant.STORY_PATH, "$section.json")
                parameter(Constant.QUERY_API_KEY, Constant.API_KEY)
            }
        }.results?.mapNotNull {
            it?.let {
                articleMapper.apply(it)
            }
        } ?: emptyList()
    }
}
