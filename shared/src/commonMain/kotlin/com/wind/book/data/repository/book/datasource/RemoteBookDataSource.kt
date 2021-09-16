package com.wind.book.data.repository.book.datasource

import com.wind.book.data.repository.book.BookAPI

interface RemoteBookDataSource {
}

internal class RemoteBookDataSourceImpl(private val bookAPI: BookAPI) : RemoteBookDataSource {

}
