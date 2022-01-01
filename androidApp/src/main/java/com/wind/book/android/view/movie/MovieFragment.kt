package com.wind.book.android.view.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.common_ui_view.databinding.ToolbarListViewBinding
import com.example.common_ui_view.util.viewBinding
import com.wind.book.android.R

class MovieFragment : Fragment(com.example.common_ui_view.R.layout.toolbar_list_view) {
    private val binding by viewBinding(ToolbarListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title = getString(R.string.movie)
            showUpBtn = false
        }
    }
}
