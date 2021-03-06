package com.wind.book.android.view.podcast

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui_view.extension.setGridSpacing
import com.example.common_ui_view.extension.spaceNormal

class PodcastDecoration(context: Context, private val spanCount: Int) : RecyclerView.ItemDecoration() {
    private val spaceNormal = context.spaceNormal.toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.setGridSpacing(parent, view, spaceNormal, spanCount)
    }
}
