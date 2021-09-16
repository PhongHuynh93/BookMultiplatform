package com.wind.book.data.repository.book

import com.wind.book.data.mapping.BookMapper
import com.wind.book.data.model.dto.BookDto
import com.wind.book.data.model.dto.BookListDto
import com.wind.book.data.model.dto.SimpleRestDto
import com.wind.book.data.util.Constant
import com.wind.book.model.Book
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

// Follow the CRUD name create-read-update-delete
interface BookAPI {
    suspend fun get(): List<Book>
}

internal class BookAPIImpl(private val client: HttpClient): BookAPI {
    private val bookMapper = BookMapper()

    override suspend fun get(): List<Book> {
        return client.get<SimpleRestDto<BookListDto>> {
            bookPath("/lists/names.json")
        }.results?.books?.mapNotNull {
            it?.let {
                bookMapper.apply(it)
            }
        } ?: emptyList()
    }
}

private fun HttpRequestBuilder.bookPath(path: String) {
    url {
        takeFrom("${Constant.HOST}${Constant.BOOK_PATH}")
        encodedPath = path
    }
}
