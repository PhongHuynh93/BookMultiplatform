package com.wind.book.android.view.podcast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.podcast.PodcastsViewModel
import com.wind.book.viewmodel.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class PodcastsFragment : Fragment(R.layout.toolbar_list_view) {
    private val vm: PodcastsViewModel by viewModel()
    private val binding by viewBinding(ToolbarListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.title_book)

        val list = binding.list

        val feedAdapter = PodcastsAdapter(
            Glide.with(this),
            object : PodcastsAdapter.Callback {
                override fun onClick(podcast: Podcast) {
                }
            }
        )
        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() {
                vm.retry()
            }
        })

        val concatAdapter =
            ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(), feedAdapter)
        binding.list.binding.apply {
            rcv.apply {
                val spanCount = 2
                layoutManager = GridLayoutManager(context, spanCount).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int) =
                            when (R.layout.load_state_item) {
                                rcv.adapter?.getItemViewType(position) -> spanCount
                                else -> 1
                            }
                    }
                }
                adapter = concatAdapter
                addItemDecoration(PodcastsDecoration(context, spanCount))
                handleLoadMore(Constant.VISIBLE_THRESHOLD, feedAdapter) {
                    vm.loadMore()
                }
            }
            swipeRefresh.apply {
                setOnRefreshListener { vm.refresh() }
            }
            loading.retryBtn.setOnClickListener {
                vm.retry()
            }
        }
        vm.apply {
            state.launchAndCollectIn(viewLifecycleOwner) {
                list.setRefreshState(it.refreshState)
                list.setLoadState(it.loadState)
                footerLoadingAdapter.loadState = it.loadState
                feedAdapter.submitList(it.data) {
                    if (it.data.isNotEmpty() && !concatAdapter.adapters.contains(footerLoadingAdapter)) {
                        concatAdapter.addAdapter(footerLoadingAdapter)
                    }
                }
            }
            effect.launchAndCollectIn(viewLifecycleOwner) {
                list.binding.rcv.scrollToPosition(0)
            }
        }
    }
}
