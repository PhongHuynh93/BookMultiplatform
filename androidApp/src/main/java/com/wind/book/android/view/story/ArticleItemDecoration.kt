package com.wind.book.android.view.story

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui_view.extension.getColorAttr
import com.example.common_ui_view.extension.getDimen
import com.example.common_ui_view.extension.spaceNormal
import com.wind.book.android.R

class ArticleItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val hozDividerSpace = context.spaceNormal
    private val dividerHeight = context.getDimen(com.example.common_ui_view.R.dimen.divider_height)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColorAttr(com.example.common_ui_view.R.attr.dividerColor)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach { view ->
            // draw the divider at the bottom of item
            val t = view.bottom.toFloat() + view.translationY
            c.drawRect(hozDividerSpace, t, parent.measuredWidth - hozDividerSpace, t + dividerHeight, paint)
        }
    }
}
