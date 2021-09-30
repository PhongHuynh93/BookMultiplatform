package com.wind.book.android.view.book

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ListViewBinding
import com.wind.book.android.extension.handleLoadMore
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.safeNavigate
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Book
import com.wind.book.model.BookName
import com.wind.book.viewmodel.LoadMoreEffect
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.book.BookEffect
import com.wind.book.viewmodel.book.BookEvent
import com.wind.book.viewmodel.book.BookViewModel
import com.wind.book.viewmodel.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFragment : Fragment(R.layout.list_view) {
    companion object {
        fun newInstance(bookName: BookName): BookFragment {
            return BookFragment().apply {
                arguments =
                    bundleOf(com.wind.book.android.util.Constant.BundleKey.BOOK_NAME to bookName)
            }
        }
    }

    private val vm: BookViewModel by viewModel()
    private val event: BookEvent
        get() {
            return vm.event
        }
    private val binding by viewBinding(ListViewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event.setBookName(requireArguments().getParcelable<BookName>(com.wind.book.android.util.Constant.BundleKey.BOOK_NAME)!!.encodedName)

        val list = binding.list

        val feedAdapter = BookAdapter(
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
                feedAdapter
            )
        binding.list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                addItemDecoration(BookItemDecoration(requireContext()))
                handleLoadMore(Constant.VISIBLE_THRESHOLD, feedAdapter) {
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
                if (screen is LoadingScreen.Data<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val data = screen.data as List<Book>
                    feedAdapter.submitList(data) {
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
