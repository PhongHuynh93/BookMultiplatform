package com.wind.book.android.view.audio

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.util.viewBinding

class AudioFragment : Fragment(R.layout.toolbar_list_view) {
    private val binding by viewBinding(ToolbarListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.title_audio)
    }
}