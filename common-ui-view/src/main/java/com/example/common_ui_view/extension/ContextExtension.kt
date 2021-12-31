package com.example.common_ui_view.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.fragment.app.FragmentActivity
import com.example.common_ui_view.R

fun Context.dp() = resources.displayMetrics.density

fun Context.getDimen(dimenRes: Int): Float {
    return resources.getDimension(dimenRes)
}

val Context.spacePrettySmall
    get() = getDimen(R.dimen.space_pretty_small)
val Context.spaceNormal
    get() = getDimen(R.dimen.space_normal)
val Context.spaceSmall
    get() = getDimen(R.dimen.space_small)
val Context.spaceLarge
    get() = getDimen(R.dimen.space_large)
val Context.spaceTiny
    get() = getDimen(R.dimen.space_tiny)
val Context.spaceAboveNormal
    get() = getDimen(R.dimen.space_above_normal)

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.getColorAttr(@AttrRes colorAttr: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(colorAttr)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun Context.getColorStateListAttr(@AttrRes colorAttr: Int): ColorStateList {
    return ColorStateList.valueOf(getColorAttr(colorAttr))
}

fun Context.screenHeight() = resources.displayMetrics.heightPixels
fun Context.screenWidth() = resources.displayMetrics.widthPixels

fun Context.getDrawableEx(drawableId: Int): Drawable {
    return ContextCompat.getDrawable(this, drawableId)!!
}

fun Context.unWrapContext(): FragmentActivity {
    var context: Context? = this
    while (context !is FragmentActivity && context is ContextWrapper) {
        context = context.baseContext
    }
    return context as FragmentActivity
}
