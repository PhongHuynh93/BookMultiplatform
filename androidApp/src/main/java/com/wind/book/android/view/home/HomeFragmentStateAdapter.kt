package com.wind.book.android.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ui_music.genre.GenreFragment
import com.wind.book.android.view.movie.MovieFragment
import com.wind.book.android.view.podcast.PodcastFragment
import com.wind.book.android.view.story.ArticleFragment

class HomeFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int) = when (position) {
        0 -> GenreFragment()
        1 -> PodcastFragment()
        2 -> MovieFragment()
        3 -> ArticleFragment()
        else -> throw IllegalArgumentException()
    }
}
