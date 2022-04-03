package com.wind.book.viewmodel.book

import com.wind.book.domain.usecase.book.GetBookListParam
import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.model.Book
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import com.wind.book.viewmodel.model.IABNav
import kotlinx.coroutines.launch

sealed class BookEffect : BaseEffect() {
    class NavToIAB(val iabNav: IABNav) : BookEffect()
}

interface BookEvent : LoadMoreEvent {
    fun onClickBuy(book: Book)
    fun setBookName(bookName: String)
}

class BookViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : LoadMoreVM<Book, BookEffect>(), BookEvent {

    override val event = this as BookEvent
    private lateinit var bookName: String

    override fun setBookName(bookName: String) {
        this.bookName = bookName
        refresh()
    }

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Book>> {
        return getBookListUseCase(
            GetBookListParam(
                currentPage = currentPage,
                listName = bookName
            )
        )
    }

    override fun onClickBuy(book: Book) {
        clientScope.launch {
            _effect.emit(
                BookEffect.NavToIAB(
                    IABNav(
                        title = book.title,
                        url = book.amazonLink
                    )
                )
            )
        }
    }
}
