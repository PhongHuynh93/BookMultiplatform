package com.wind.book.android.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wind.book.android.R
import com.wind.book.android.databinding.HomeBinding
import com.wind.book.android.util.viewBinding

class HomeFragment : Fragment(R.layout.home) {
    private val binding by viewBinding(HomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPager.apply {
            offscreenPageLimit = 1
            adapter = HomeFragmentStateAdapter(this@HomeFragment)
            isUserInputEnabled = false
        }
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_book -> viewPager.setCurrentItem(0, false)
                R.id.navigation_podcast -> viewPager.setCurrentItem(1, false)
                R.id.navigation_movie -> viewPager.setCurrentItem(2, false)
                R.id.navigation_story -> viewPager.setCurrentItem(3, false)
            }
            return@setOnItemSelectedListener true
        }
    }
}
