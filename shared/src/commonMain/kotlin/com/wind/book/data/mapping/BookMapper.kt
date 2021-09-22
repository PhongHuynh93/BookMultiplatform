package com.wind.book.data.mapping

import com.wind.book.data.model.dto.BookDto
import com.wind.book.model.Book
import com.wind.book.model.Thumb

class BookMapper : Mapper<BookDto, Book?> {
    override fun apply(input: BookDto): Book? {
        return input.run {
            val bookImageWidth = bookImageWidth ?: return null
            if (bookImageWidth <= 0) {
                return null
            }
            val bookImageHeight = bookImageHeight ?: return null
            if (bookImageHeight <= 0) {
                return null
            }
            Book(
                id = rank?.toString() ?: return null,
                author = author.orEmpty(),
                thumb = Thumb(
                    url = bookImage.orEmpty(),
                    width = bookImageWidth,
                    height = bookImageHeight,
                ),
                description = description.orEmpty(),
                title = title.orEmpty(),
                amazonLink = amazonProductUrl.orEmpty()
            )
        }
    }
}
