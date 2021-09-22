package com.wind.book.android.extension

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.smoothAndFastScrollToTop() {
    smoothAndFastScrollTo(0)
}

fun RecyclerView.smoothAndFastScrollTo(to: Int) {
    (layoutManager as? LinearLayoutManager)?.let { layoutManager ->
        // don't use findFirstCompletelyVisibleItemPosition because in case the viewHolder has large height,
        // it will return -1 and we can't scroll
        val last = layoutManager.findLastVisibleItemPosition()
        val first = layoutManager.findFirstVisibleItemPosition()
        val currentPos = (first + last) / 2
        val threshold = (last - first).coerceAtLeast(1) * 2 // 2 pages
        if (currentPos >= 0) {
            if (to - currentPos > threshold) {
                // scroll down
                layoutManager.scrollToPosition(to - threshold)
            } else if (currentPos - to > threshold) {
                // scroll up
                layoutManager.scrollToPosition(to + threshold)
            }
            smoothScrollToPosition(to)
        }
    }
}

private const val VISIBLE_THRESHOLD = 20
fun RecyclerView.handleLoadMore(visibleThreshold: Int? = null, getItemCountCallback: GetItemCountCallback, callback: () -> Unit) {
    val threshold = visibleThreshold ?: VISIBLE_THRESHOLD
    val layoutManager = layoutManager as LinearLayoutManager
    val checkScroll: (layoutManager: LinearLayoutManager, callback: () -> Unit) -> Unit =
        { lm, cb ->
            val totalItemCount = getItemCountCallback.getItemCount()
            val lastVisibleItem = lm.findLastVisibleItemPosition()
            if (totalItemCount <= (lastVisibleItem + threshold)) {
                cb.invoke()
            }
        }
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            checkScroll(layoutManager, callback)
        }
    })
}

fun interface GetItemCountCallback {
    fun getItemCount(): Int
}

fun Rect.setGridSpacing(parent: RecyclerView, view: View, spacing: Int, spanCount: Int) {
    val position = parent.getChildAdapterPosition(view)
    val spanIndex = position % spanCount
    left = spacing - spanIndex * spacing / spanCount
    right = (spanIndex + 1) * spacing / spanCount
    top = if (position < spanCount) spacing else 0
    bottom = spacing
}