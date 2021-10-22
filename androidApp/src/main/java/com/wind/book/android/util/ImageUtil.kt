package com.wind.book.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter(
    "url",
)
fun ImageView.loadImage(url: String?) {
    load(url)
}
