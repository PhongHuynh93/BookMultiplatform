package com.wind.book.android.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.common_ui_view.extension.launchAndRepeatCollectInResume
import com.example.common_ui_view.extension.safeNavigate
import com.example.common_ui_view.util.openArtist
import com.example.common_ui_view.util.viewBinding
import com.wind.book.android.R
import com.wind.book.android.databinding.HomeBinding

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
                R.id.navigation_music -> viewPager.setCurrentItem(0, false)
                R.id.navigation_podcast -> viewPager.setCurrentItem(1, false)
                R.id.navigation_movie -> viewPager.setCurrentItem(2, false)
                R.id.navigation_story -> viewPager.setCurrentItem(3, false)
            }
            return@setOnItemSelectedListener true
        }
        openArtist.launchAndRepeatCollectInResume(viewLifecycleOwner) {
            findNavController().safeNavigate(
                HomeFragmentDirections.actionHomeFragmentToMusicGraph(
                    it
                )
            )
        }
    }
}
