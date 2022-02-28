package com.wind.book.data.repository.book

import com.wind.book.data.mapping.BookMapper
import com.wind.book.data.mapping.BookNameMapper
import com.wind.book.data.model.dto.BookNameListDto
import com.wind.book.data.model.dto.BookResDto
import com.wind.book.data.util.BaseAPI
import com.wind.book.data.util.Constant
import com.wind.book.model.Book
import com.wind.book.model.BookName
import io.ktor.client.request.parameter

// Follow the CRUD name create-read-update-delete
interface BestSellerAPI {
    suspend fun get(currentPage: Int, listName: String): List<Book>
    suspend fun getNames(): List<BookName>
}

internal class BestSellerAPIImpl : BaseAPI(), BestSellerAPI {
    private val bookMapper = BookMapper()
    private val bookNameMapper = BookNameMapper()

    override val baseUrl: String
        get() = Constant.Book.HOST

    override suspend fun get(currentPage: Int, listName: String): List<Book> {
        return doGet<BookResDto> {
            apiPath(Constant.Book.BOOK_PATH, "current", "$listName.json")
            parameter(Constant.Book.QUERY_OFFSET, currentPage.toString())
            parameter(Constant.Book.QUERY_API_KEY, Constant.Book.API_KEY)
        }.results?.books?.mapNotNull {
            it?.let {
                bookMapper.apply(it)
            }
        } ?: emptyList()
    }

    override suspend fun getNames(): List<BookName> {
        return doGet<BookNameListDto> {
            apiPath(Constant.Book.BOOK_PATH, "names.json")
            parameter(Constant.Book.QUERY_API_KEY, Constant.Book.API_KEY)
        }.results?.mapNotNull {
            it?.let {
                bookNameMapper.apply(it)
            }
        } ?: emptyList()
    }
}
