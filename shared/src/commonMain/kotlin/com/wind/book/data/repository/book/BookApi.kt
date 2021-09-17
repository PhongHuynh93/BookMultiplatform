package com.wind.book.data.repository.book

import com.wind.book.data.mapping.BookMapper
import com.wind.book.data.model.dto.BookResDto
import com.wind.book.data.util.Constant
import com.wind.book.model.Book
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

// Follow the CRUD name create-read-update-delete
interface BestSellerAPI {
    suspend fun get(currentPage: Int, listName: String): List<Book>
}

internal class BestSellerAPIImpl(private val client: HttpClient): BestSellerAPI {
    private val bookMapper = BookMapper()

    override suspend fun get(currentPage: Int, listName: String): List<Book> {
        return client.get<BookResDto> {
            url {
                takeFrom(Constant.HOST)
                path(Constant.BOOK_PATH, "current", "${listName}.json")
                parameter(Constant.QUERY_OFFSET, currentPage.toString())
                parameter(Constant.QUERY_API_KEY, Constant.API_KEY)
            }
        }.results?.books?.mapNotNull {
            it?.let {
                bookMapper.apply(it)
            }
        } ?: emptyList()
    }
}
