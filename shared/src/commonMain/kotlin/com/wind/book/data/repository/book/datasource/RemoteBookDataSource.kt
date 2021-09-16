package com.wind.book.data.repository.book.datasource

import com.wind.book.data.repository.book.BestSellerAPI
import com.wind.book.model.Book

interface RemoteBookDataSource {
    suspend fun getBookList(listName: String): List<Book>
}

internal class RemoteBookDataSourceImpl(private val bestSellerAPI: BestSellerAPI) : RemoteBookDataSource {
    override suspend fun getBookList(listName: String): List<Book> {
        return bestSellerAPI.get(listName)
    }
}
