package com.wind.book.android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.wind.book.android.R
import jp.wasabeef.glide.transformations.BlurTransformation

@BindingAdapter(
    "rm",
    "url",
    "width",
    "height",
    requireAll = false
)
fun ImageView.loadImage(requestManager: RequestManager, url: String?, width: Int? = null, height: Int? = null) {
    getRequestBuilder(requestManager, url, width, height)
        .into(this)
}

@BindingAdapter(
    "rm",
    "blurUrl",
    "width",
    "height",
    requireAll = false
)
fun ImageView.loadBlurImage(requestManager: RequestManager, url: String?, width: Int? = null, height: Int? = null) {
    getRequestBuilder(requestManager, url, width, height)
        .transform(CenterCrop(), BlurTransformation(25, 6))
        .into(this)
}

fun getRequestBuilder(
    requestManager: RequestManager,
    url: String?,
    width: Int? = null,
    height: Int? = null
): RequestBuilder<*> {
    val requestOptions: RequestOptions = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    return requestManager
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .apply(requestOptions)
        .let {
            if (width != null && height != null) {
                it.override(width, height)
            } else {
                it
            }
        }
}
