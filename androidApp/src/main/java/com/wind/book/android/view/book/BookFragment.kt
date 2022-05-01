package com.wind.book.android.view.book

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.common_ui_view.adapter.LoadingAdapter
import com.example.common_ui_view.extension.handleLoadMore
import com.example.common_ui_view.extension.launchAndCollectIn
import com.example.common_ui_view.extension.safeNavigate
import com.example.common_ui_view.util.tryCast
import com.example.common_ui_view.util.viewBinding
import com.wind.book.android.R
import com.wind.book.android.databinding.ListViewBinding
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Book
import com.wind.book.model.BookName
import com.wind.book.viewmodel.book.BookEffect
import com.wind.book.viewmodel.book.BookEvent
import com.wind.book.viewmodel.book.BookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.test.common_model.LoadingScreen

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
                screen.tryCast<LoadingScreen.Data<Book>> {
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
            effect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
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
