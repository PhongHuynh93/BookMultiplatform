package com.wind.book.android.binding

import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.extension.spaceLarge

@BindingAdapter("defaultVerList")
fun RecyclerView.defaultVerList(defaultVerList: Boolean? = null) {
    // default rcv uses LinearLayoutManager
    if (defaultVerList == null || defaultVerList == true) {
        layoutManager = LinearLayoutManager(context)
    }
    setHasFixedSize(true)
}

@BindingAdapter("paddingBottom")
fun RecyclerView.bindPaddingBottom(_paddingBottom: Int? = null) {
    val paddingBottom = _paddingBottom ?: context.spaceLarge.toInt()
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val pos = parent.getChildViewHolder(view).absoluteAdapterPosition
            (pos != -1 && (pos == (parent.adapter?.itemCount ?: 0) - 1)).let {
                if (it) {
                    outRect.bottom = paddingBottom
                }
            }
        }
    })
}

@BindingAdapter("defaultHozList")
fun RecyclerView.bindDefaultSetup(defaultHozList: Boolean? = null) {
    // default rcv uses LinearLayoutManager
    if (defaultHozList == null || defaultHozList == true) {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}
