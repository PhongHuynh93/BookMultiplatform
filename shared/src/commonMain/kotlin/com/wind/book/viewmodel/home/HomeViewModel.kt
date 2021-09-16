package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.book.GetBookListParam
import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.viewmodel.BaseViewModel
import com.wind.book.viewmodel.toastError
import kotlinx.coroutines.flow.flow

// TODO: 16/09/2021 handle paging
class HomeViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : BaseViewModel() {
    val bookList = flow {
        // TODO: 16/09/2021 get list name first, not hard code here
        getBookListUseCase(GetBookListParam("hardcover-fiction"))
            .onSuccess {
                emit(it)
            }
            .onFailure {
                toastError.emit(it)
            }
    }
}