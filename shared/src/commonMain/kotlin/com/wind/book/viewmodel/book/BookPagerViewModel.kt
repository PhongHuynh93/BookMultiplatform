package com.wind.book.viewmodel.book

import com.wind.book.domain.usecase.book.GetBookNameParam
import com.wind.book.domain.usecase.book.GetBookNameUseCase
import com.wind.book.model.BookName
import com.wind.book.share.WhileViewSubscribed
import com.wind.book.toastError
import com.wind.book.viewmodel.BaseEffect
import com.wind.book.viewmodel.BaseEvent
import com.wind.book.viewmodel.BaseMVIViewModel
import com.wind.book.viewmodel.BaseState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

data class BookPagerState(
    val bookNames: List<BookName>
) : BaseState() {

    // Need secondary constructor to initialize with no args in SwiftUI
    constructor() : this(
        bookNames = emptyList()
    )
}

sealed class BookPagerEffect : BaseEffect()

interface BookPagerEvent : BaseEvent

class BookPagerViewModel(
    private val getBookNameUseCase: GetBookNameUseCase
) : BaseMVIViewModel(), BookPagerEvent {

    override val state: StateFlow<BookPagerState> = flow {
        getBookNameUseCase(GetBookNameParam())
            .onSuccess {
                emit(BookPagerState(it))
            }
            .onFailure {
                toastError.emit(it)
            }
    }.stateIn(
        scope = clientScope,
        started = WhileViewSubscribed,
        initialValue = BookPagerState()
    )

    private val _effect: MutableSharedFlow<BookPagerEffect> = MutableSharedFlow()
    override val effect = _effect.asSharedFlow()

    override val event: BookPagerEvent
        get() = this
}
