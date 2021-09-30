package com.wind.book.data.repository.book.datasource

import com.wind.book.data.repository.book.BestSellerAPI
import com.wind.book.model.Book
import com.wind.book.model.BookName

interface RemoteBookDataSource {
    suspend fun getBookList(currentPage: Int, listName: String): List<Book>
    suspend fun getNames(): List<BookName>
}

internal class RemoteBookDataSourceImpl(private val bestSellerAPI: BestSellerAPI) :
    RemoteBookDataSource {
    override suspend fun getBookList(currentPage: Int, listName: String): List<Book> {
        return bestSellerAPI.get(currentPage, listName)
    }

    override suspend fun getNames(): List<BookName> {
        return bestSellerAPI.getNames()
    }
}
