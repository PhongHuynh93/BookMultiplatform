package com.wind.book.data.repository.book

import com.wind.book.data.repository.book.datasource.LocalBookDataSource
import com.wind.book.data.repository.book.datasource.RemoteBookDataSource
import com.wind.book.model.Book
import com.wind.book.model.BookName

interface BookRepository {
    suspend fun getBookList(currentPage: Int, listName: String): List<Book>
    suspend fun getNames(): List<BookName>
}

internal class BookRepositoryImpl(
    private val remoteSource: RemoteBookDataSource,
    private val localSource: LocalBookDataSource
) : BookRepository {
    override suspend fun getBookList(currentPage: Int, listName: String): List<Book> {
        return remoteSource.getBookList(currentPage, listName)
    }

    override suspend fun getNames(): List<BookName> {
        return remoteSource.getNames()
    }
}
