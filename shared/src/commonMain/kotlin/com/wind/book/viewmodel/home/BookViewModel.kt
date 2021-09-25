package com.wind.book.viewmodel.home

import com.wind.book.domain.usecase.book.GetBookListParam
import com.wind.book.domain.usecase.book.GetBookListUseCase
import com.wind.book.model.Book
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.LoadMoreEffect
import com.wind.book.viewmodel.LoadMoreEvent
import com.wind.book.viewmodel.LoadMoreVM
import com.wind.book.viewmodel.model.IABNav
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class BookEffect : BaseEffect() {
    class LMEffect(val loadMoreEffect: LoadMoreEffect) : BookEffect()
    class NavToIAB(val iabNav: IABNav) : BookEffect()
}

interface BookEvent : LoadMoreEvent {
    fun onClickBuy(book: Book)
}

class BookViewModel(
    private val getBookListUseCase: GetBookListUseCase
) : LoadMoreVM<Book>(), BookEvent {

    private val _bookEffect = MutableSharedFlow<BookEffect>()
    val bookEffect = _bookEffect.asSharedFlow()
    override val event = this as BookEvent

    init {
        loadMore()
        // capture the base effect and emit again
        clientScope.launch {
            effect.collectLatest {
                _bookEffect.emit(BookEffect.LMEffect(it))
            }
        }
    }

    override suspend fun apiCall(
        currentPage: Int,
        pageSize: Int,
        isRefresh: Boolean
    ): Result<List<Book>> {
        return getBookListUseCase(
            GetBookListParam(
                currentPage = currentPage,
                listName = "hardcover-fiction"
            )
        )
    }

    override fun onClickBuy(book: Book) {
        clientScope.launch {
            _bookEffect.emit(
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
