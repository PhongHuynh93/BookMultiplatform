package com.wind.book.android.extension

import android.animation.Animator
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun View.setWidthForItemInHozRecyclerView(width: Int) {
    val params = RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    layoutParams = params
}

fun View.addRippleBorderless() {
    if (context.theme == null) return
    val a = context.theme.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackgroundBorderless))
    val d = a.getDrawable(0)
    a.recycle()
    foreground = d
}

fun View.addRipple() {
    if (context.theme == null) return
    val a = context.theme.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackground))
    val d = a.getDrawable(0)
    a.recycle()
    foreground = d
}

@BindingAdapter(
    "isVisible"
)
fun View.isVisible(visible: Boolean) {
    isVisible = visible
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.fadeIn(duration: Long = 300L) {
    show()
    animate().alpha(1f).setDuration(duration).start()
}

fun View.fadeOut(duration: Long = 300L) {
    animate().alpha(0f)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                gone()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
        .setDuration(duration)
        .start()
}

fun View.delayOnLifecycle(
    durationInMillis: Long,
    block: () -> Unit
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
        delay(durationInMillis)
        block()
    }
}

fun View.getToolbarHeight(): Int {
    val tv = TypedValue()
    return if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    } else {
        0
    }
}

suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->
    // This lambda is invoked immediately, allowing us to create
    // a callback/listener

    val listener = object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            // The next layout has happened!
            // First remove the listener to not leak the coroutine
            v.removeOnLayoutChangeListener(this)
            // Finally resume the continuation, and
            // wake the coroutine up
            cont.resume(Unit)
        }
    }
    // If the coroutine is cancelled, remove the listener
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    // And finally add the listener to view
    addOnLayoutChangeListener(listener)

    // The coroutine will now be suspended. It will only be resumed
    // when calling cont.resume() in the listener above
}

suspend fun View.awaitLayout() = suspendCancellableCoroutine<Unit> { cont ->
    if (width > 0 && height > 0) {
        cont.resume(Unit)
    } else {
        val listener = object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                v.removeOnLayoutChangeListener(this)
                cont.resume(Unit)
            }
        }
        cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
        addOnLayoutChangeListener(listener)
    }
}
