package com.wind.book.android.view.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.safeNavigate
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Book
import com.wind.book.viewmodel.LoadMoreEffect
import com.wind.book.viewmodel.home.BookEffect
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

        val feedAdapter = BookAdapter(
            Glide.with(this),
            object : BookAdapter.Callback {
                override fun onClickBuyBtn(book: Book) {
                    vm.onClickBuy(book)
                }
            }
        )
        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() {
                vm.retry()
            }
        })

        val concatAdapter =
            ConcatAdapter(
                ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
                feedAdapter
            )
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
            bookEffect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is BookEffect.LMEffect -> {
                        when (it.loadMoreEffect) {
                            LoadMoreEffect.ScrollToTop -> list.binding.rcv.scrollToPosition(0)
                        }
                    }
                    is BookEffect.NavToIAB -> {
                        findNavController().safeNavigate(
                            HomeFragmentDirections.actionHomeFragmentToIABFragment(it.iabNav)
                        )
                    }
                }
            }
        }
    }
}
