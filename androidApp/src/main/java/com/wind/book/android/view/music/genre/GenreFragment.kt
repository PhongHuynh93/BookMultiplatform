package com.wind.book.android.view.music.genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.GenreBinding
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.spaceNormal
import com.wind.book.android.util.GridItemDecoration
import com.wind.book.android.util.tryCast
import com.wind.book.android.util.viewBinding
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.genre.GenreEffect
import com.wind.book.viewmodel.music.genre.GenreEvent
import com.wind.book.viewmodel.music.genre.GenreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class GenreFragment : Fragment(R.layout.genre) {
    private val binding by viewBinding(GenreBinding::bind)
    private val vm: GenreViewModel by viewModel()
    private val event: GenreEvent
        get() {
            return vm.event
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = binding.list
        val genreAdapter = GenreAdapter(
            Glide.with(this),
            event
        )
        list.binding.apply {
            rcv.apply {
                adapter = genreAdapter
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                addItemDecoration(
                    GridItemDecoration(
                        requireContext().spaceNormal.toInt(),
                        true,
                        SPAN_COUNT
                    )
                )
            }
            swipeRefresh.apply {
                setOnRefreshListener { event.refresh() }
            }
            loading.retryBtn.setOnClickListener {
                event.retry()
            }
        }
        vm.apply {
            state.launchAndCollectIn(viewLifecycleOwner) {
                val screen = it.screen
                screen.tryCast<LoadingScreen.Data<Genre>> {
                    genreAdapter.submitList(data)
                }
                list.setScreen(screen)
            }
            genreEffect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is GenreEffect.LoadingEffect -> {
                    }
                }
            }
        }
    }
}
