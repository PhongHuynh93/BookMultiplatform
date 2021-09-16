package com.wind.book.data.repository.book.datasource

import com.wind.book.data.repository.book.BookAPI
import com.wind.book.model.Book

interface RemoteBookDataSource {
    suspend fun getBookList(): List<Book>
}

internal class RemoteBookDataSourceImpl(private val bookAPI: BookAPI) : RemoteBookDataSource {
    override suspend fun getBookList(): List<Book> {
        return bookAPI.get()
    }
}
