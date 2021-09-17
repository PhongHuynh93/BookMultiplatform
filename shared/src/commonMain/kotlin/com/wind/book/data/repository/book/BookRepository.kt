package com.wind.book.data.repository.book

import com.wind.book.data.repository.book.datasource.LocalBookDataSource
import com.wind.book.data.repository.book.datasource.RemoteBookDataSource
import com.wind.book.model.Book

interface BookRepository {
    suspend fun getBookList(currentPage: Int, listName: String): List<Book>
}

internal class BookRepositoryImpl(
    private val remoteSource: RemoteBookDataSource,
    private val localSource: LocalBookDataSource
) : BookRepository {
    override suspend fun getBookList(currentPage: Int, listName: String): List<Book> {
        return remoteSource.getBookList(currentPage, listName)
    }
}
