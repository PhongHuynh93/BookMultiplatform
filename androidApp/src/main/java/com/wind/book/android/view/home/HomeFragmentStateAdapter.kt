package com.wind.book.android.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wind.book.android.view.book.BookFragment
import com.wind.book.android.view.movie.MovieFragment
import com.wind.book.android.view.podcast.PodcastsFragment

class HomeFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> BookFragment()
        1 -> PodcastsFragment()
        2 -> MovieFragment()
        else -> throw IllegalArgumentException()
    }
}
