package com.example.ui_music.artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common_ui_view.adapter.LoadingAdapter
import com.example.common_ui_view.databinding.ToolbarListViewBinding
import com.example.common_ui_view.extension.handleLoadMore
import com.example.common_ui_view.extension.launchAndCollectIn
import com.example.common_ui_view.extension.spaceNormal
import com.example.common_ui_view.util.GridItemDecoration
import com.example.common_ui_view.util.tryCast
import com.example.common_ui_view.util.viewBinding
import com.example.ui_music.R
import com.wind.book.model.music.Artist
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.music.artist.ArtistEffect
import com.wind.book.viewmodel.music.artist.ArtistEvent
import com.wind.book.viewmodel.music.artist.ArtistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class ArtistFragment : Fragment(R.layout.toolbar_list_view) {
    private val binding by viewBinding(ToolbarListViewBinding::bind)
    private val vm: ArtistViewModel by viewModel()
    private val event: ArtistEvent
        get() {
            return vm.event
        }
    private val args: ArtistFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event.setGenreId(args.genre.id)
        binding.title = args.genre.model.name

        val list = binding.list
        val artistAdapter = ArtistAdapter(
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
                handleLoadMore {
                    event.loadMore(it)
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
