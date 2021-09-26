package com.wind.book.android.binding

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout
import com.wind.book.android.R
import com.wind.book.android.extension.unWrapContext

@BindingAdapter(
    "showUpBtn",
    "useCloseIcon",
    requireAll = false
)
fun Toolbar.showUpBtn(showUpBtn: Boolean?, useCloseIcon: Boolean? = false) {
    if (showUpBtn == null || showUpBtn == true) {
        if (useCloseIcon == true) {
            setNavigationIcon(R.drawable.ic_baseline_close_24)
        } else {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        }
        setNavigationOnClickListener {
            it.context.unWrapContext().onBackPressed()
        }
    }
}

fun Toolbar.setLayoutScrollBehavior(scrollFlag: Int) {
    (layoutParams as AppBarLayout.LayoutParams).apply {
        scrollFlags = scrollFlag
    }
}
