package com.wind.book.android.view.music.artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.binding.showUpBtn
import com.wind.book.android.databinding.ToolbarGridViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.spaceNormal
import com.wind.book.android.util.GridItemDecoration
import com.wind.book.android.util.tryCast
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.model.music.Artist
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.artist.ArtistEffect
import com.wind.book.viewmodel.music.artist.ArtistEvent
import com.wind.book.viewmodel.music.artist.ArtistViewModel
import com.wind.book.viewmodel.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class ArtistFragment : Fragment(R.layout.toolbar_grid_view) {
    private val binding by viewBinding(ToolbarGridViewBinding::bind)
    private val vm: ArtistViewModel by viewModel()
    private val event: ArtistEvent
        get() {
            return vm.event
        }
    private val args: ArtistFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.genreId = args.genre.id
        binding.title = args.genre.model.name
        binding.toolbar.showUpBtn(showUpBtn = true)

        val list = binding.list
        val artistAdapter = ArtistAdapter(
            Glide.with(this),
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
                artistAdapter
            )

        list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                addItemDecoration(
                    GridItemDecoration(
                        requireContext().spaceNormal.toInt(),
                        true,
                        SPAN_COUNT
                    )
                )
                handleLoadMore(Constant.VISIBLE_THRESHOLD, artistAdapter) {
                    event.loadMore()
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
                screen.tryCast<LoadingScreen.Data<Artist>> {
                    artistAdapter.submitList(data) {
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
            artistEffect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is ArtistEffect.LoadMoreEffect -> {
                    }
                }
            }
        }
    }
}