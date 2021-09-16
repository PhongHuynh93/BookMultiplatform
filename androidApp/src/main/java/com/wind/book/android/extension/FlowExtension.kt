package com.wind.book.android.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.launchAndRepeatCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launchWhenStarted {
    collectLatest {
        action(it)
    }
}

inline fun <T> Flow<T>.launchAndCollectOnCreate(
    owner: LifecycleOwner,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launchWhenCreated {
    collectLatest {
        action(it)
    }
}

fun <T> delayFlow(timeout: Long, value: T): Flow<T> = flow {
    delay(timeout)
    emit(value)
}
