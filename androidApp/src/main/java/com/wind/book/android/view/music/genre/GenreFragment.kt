package com.wind.book.android.view.music.genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarGridViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.safeNavigate
import com.wind.book.android.extension.spaceNormal
import com.wind.book.android.util.GridItemDecoration
import com.wind.book.android.util.tryCast
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.genre.GenreEffect
import com.wind.book.viewmodel.music.genre.GenreEvent
import com.wind.book.viewmodel.music.genre.GenreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class GenreFragment : Fragment(R.layout.toolbar_grid_view) {
    private val binding by viewBinding(ToolbarGridViewBinding::bind)
    private val vm: GenreViewModel by viewModel()
    private val event: GenreEvent
        get() {
            return vm.event
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title = getString(R.string.genre)

        val list = binding.list
        val genreAdapter = GenreAdapter(
            event
        )
        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() {
                event.retry()
            }
        })
        val concatAdapter =
            ConcatAdapter(
                ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                genreAdapter
            )

        list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (concatAdapter.getItemViewType(position) == R.layout.load_state_item) {
                                2
                            } else {
                                1
                            }
                        }
                    }
                }
                addItemDecoration(
                    GridItemDecoration(
                        requireContext().spaceNormal.toInt(),
                        true,
                        SPAN_COUNT
                    )
                )
                handleLoadMore {
                    event.loadMore(rcv.getChildViewHolder(it).bindingAdapterPosition)
                }
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
                    genreAdapter.submitList(data) {
                        if (data.isNotEmpty() && !concatAdapter.adapters.contains(
                                footerLoadingAdapter
                            )
                        ) {
                            concatAdapter.addAdapter(footerLoadingAdapter)
                        }
                    }
                }
                list.setScreen(screen)
                footerLoadingAdapter.loadState = screen
            }
            genreEffect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is GenreEffect.LoadMoreEffect -> {
                    }
                    is GenreEffect.NavToArtist -> {
                        findNavController().safeNavigate(
                            com.wind.book.android.view.home.HomeFragmentDirections
                                .actionHomeFragmentToArtistFragment(it.genre)
                        )
                    }
                }
            }
        }
    }
}
