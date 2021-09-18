package com.wind.book.android.view.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.model.Book
import com.wind.book.viewmodel.home.BookViewModel
import com.wind.book.viewmodel.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFragment : Fragment(R.layout.toolbar_list_view) {
    private val vm: BookViewModel by viewModel()
    private val binding by viewBinding(ToolbarListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.title_book)

        val list = binding.list

        val feedAdapter = BookAdapter(Glide.with(this), object : BookAdapter.Callback {
            override fun onClick(view: View, pos: Int, item: Book) {
                // TODO: 17/09/2021 handle on click book
            }
        })
        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() {
                vm.retry()
            }
        })

        val concatAdapter =
            ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(), feedAdapter)
        binding.list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                addItemDecoration(BookItemDecoration(requireContext()))
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
            refreshState.launchAndCollectIn(viewLifecycleOwner) {
                list.setRefreshState(it)
            }
            loadState.launchAndCollectIn(viewLifecycleOwner) { loadState ->
                list.setLoadState(loadState)
                footerLoadingAdapter.loadState = loadState
            }
            data.launchAndCollectIn(viewLifecycleOwner) {
                feedAdapter.submitList(it) {
                    if (it.isNotEmpty() && !concatAdapter.adapters.contains(footerLoadingAdapter)) {
                        concatAdapter.addAdapter(footerLoadingAdapter)
                    }
                }
            }
            scrollToTop.launchAndCollectIn(viewLifecycleOwner) {
                list.binding.rcv.scrollToPosition(0)
            }
        }
    }
}