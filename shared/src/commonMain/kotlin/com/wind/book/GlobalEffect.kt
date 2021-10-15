package com.wind.book

import kotlinx.coroutines.flow.MutableSharedFlow

val toastError: MutableSharedFlow<Throwable> = MutableSharedFlow()
