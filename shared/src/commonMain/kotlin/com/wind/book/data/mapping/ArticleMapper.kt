package com.wind.book.data.mapping

import com.wind.book.data.model.dto.ArticleDto
import com.wind.book.model.Article

class ArticleMapper : Mapper<ArticleDto, Article?> {
    private val multiMediasMapper = MultiMediasMapper()
    override fun apply(input: ArticleDto): Article? {
        return input.run {
            Article(
                section = section.orEmpty(),
                subSection = subSection.orEmpty(),
                abstract = abstract.orEmpty(),
                title = title.orEmpty(),
                url = url.orEmpty(),
                multimedia = multiMediasMapper.apply(multimedia),
                date = publishedDate.orEmpty()
            )
        }
    }
}
