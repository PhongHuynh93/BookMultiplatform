package com.wind.book.viewmodel

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseMVIViewModel : BaseViewModel() {
    abstract val state: StateFlow<BaseState>?
    abstract val effect: SharedFlow<BaseEffect>?
    abstract val event: BaseEvent?
}
