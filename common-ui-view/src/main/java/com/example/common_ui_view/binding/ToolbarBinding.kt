package com.example.common_ui_view.binding

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.example.common_ui_view.R
import com.example.common_ui_view.extension.unWrapContext
import com.google.android.material.appbar.AppBarLayout

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
