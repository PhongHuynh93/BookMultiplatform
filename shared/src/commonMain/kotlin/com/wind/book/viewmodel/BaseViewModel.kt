package com.wind.book.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

val toastError: MutableSharedFlow<Throwable> = MutableSharedFlow()

@Suppress("EmptyDefaultConstructor")
expect open class BaseViewModel() {
    protected val clientScope: CoroutineScope
    protected open fun onCleared()
}
