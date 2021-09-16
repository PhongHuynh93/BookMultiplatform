package com.wind.book.android.extension

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

fun Fragment.getCurrentFragmentInViewPager(viewPager: ViewPager2): Fragment? {
    return childFragmentManager.findFragmentByTag("f" + viewPager.currentItem)
}

fun Fragment.dp() = resources.displayMetrics.density

val Fragment.spacePrettySmall
    get() = requireContext().spacePrettySmall
val Fragment.spaceNormal
    get() = requireContext().spaceNormal
val Fragment.spaceSmall
    get() = requireContext().spaceSmall
val Fragment.spaceLarge
    get() = requireContext().spaceLarge
val Fragment.spaceTiny
    get() = requireContext().spaceTiny
val Fragment.spaceAboveNormal
    get() = requireContext().spaceAboveNormal
