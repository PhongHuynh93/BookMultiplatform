package com.wind.book.android.view.podcast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.common_ui_view.adapter.LoadingAdapter
import com.example.common_ui_view.databinding.ToolbarListViewBinding
import com.example.common_ui_view.extension.handleLoadMore
import com.example.common_ui_view.extension.launchAndCollectIn
import com.example.common_ui_view.extension.safeNavigate
import com.example.common_ui_view.util.viewBinding
import com.wind.book.android.R
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.podcast.PodcastEffect
import com.wind.book.viewmodel.podcast.PodcastEvent
import com.wind.book.viewmodel.podcast.PodcastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.test.common_model.LoadingScreen

class PodcastFragment : Fragment(com.example.common_ui_view.R.layout.toolbar_list_view) {
    private val vm: PodcastViewModel by viewModel()
    private val event: PodcastEvent
        get() = vm.event
    private val binding by viewBinding(ToolbarListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title = getString(R.string.book)
            showUpBtn = false
        }
        val list = binding.list

        val podcastAdapter = PodcastAdapter(
            object : PodcastAdapter.Callback {
                override fun onClick(podcast: Podcast) = event.onClick(podcast)
            }
        )

        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() = event.retry()
        })

        val concatAdapter =
            ConcatAdapter(
                ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                podcastAdapter
            )
        binding.list.binding.apply {
            rcv.apply {
                val spanCount = 2
                layoutManager = GridLayoutManager(context, spanCount).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int) =
                            when (com.example.common_ui_view.R.layout.load_state_item) {
                                rcv.adapter?.getItemViewType(position) -> spanCount
                                else -> 1
                            }
                    }
                }
                adapter = concatAdapter
                addItemDecoration(PodcastDecoration(context, spanCount))
                handleLoadMore {
                    event.loadData(it)
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
                if (screen is LoadingScreen.Data<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val data = screen.data as List<Podcast>
                    podcastAdapter.submitList(data) {
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
            effect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is PodcastEffect.NavToDetail -> {
                        findNavController().safeNavigate(
                            HomeFragmentDirections.actionHomeFragmentToPodcastDetailFragment(
                                it.podcast
                            )
                        )
                    }
                }
            }
        }
    }
}
