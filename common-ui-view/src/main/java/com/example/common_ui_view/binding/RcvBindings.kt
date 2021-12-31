package com.example.common_ui_view.binding

import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui_view.extension.spaceLarge

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
    updatePadding(bottom = paddingBottom)
    clipToPadding = false
}

@BindingAdapter("defaultHozList")
fun RecyclerView.bindDefaultSetup(defaultHozList: Boolean? = null) {
    // default rcv uses LinearLayoutManager
    if (defaultHozList == null || defaultHozList == true) {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}
