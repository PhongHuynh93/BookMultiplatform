package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.book.GetBookListParam
import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.model.Book
import com.wind.book.viewmodel.LoadMoreVM

class HomeViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : LoadMoreVM<Book>() {

    init {
        loadMore()
    }

    override suspend fun apiCall(currentPage: Int, pageSize: Int, isRefresh: Boolean): Result<List<Book>> {
        return getBookListUseCase(GetBookListParam(currentPage = currentPage, listName = "hardcover-fiction"))
    }
}