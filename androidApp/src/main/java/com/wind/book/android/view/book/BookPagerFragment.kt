package com.wind.book.android.view.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wind.book.android.R
import com.wind.book.android.databinding.BookPagerBinding
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.util.viewBinding
import com.wind.book.model.BookName
import com.wind.book.viewmodel.book.BookPagerEvent
import com.wind.book.viewmodel.book.BookPagerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookPagerFragment : Fragment(R.layout.book_pager) {
    private val binding by viewBinding(BookPagerBinding::bind)
    private val vm: BookPagerViewModel by viewModel()
    private val event: BookPagerEvent
        get() {
            return vm.event
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookPagerAdapter =
            object : FragmentStateAdapter(childFragmentManager, viewLifecycleOwner.lifecycle) {
                val bookNames = mutableListOf<BookName>()

                override fun getItemCount(): Int {
                    return bookNames.size
                }

                override fun createFragment(position: Int): Fragment {
                    return BookFragment.newInstance(bookName = bookNames[position])
                }

                fun updateBookNames(list: List<BookName>) {
                    val callback = BookNameDiffUtil(bookNames, list)
                    val diff = DiffUtil.calculateDiff(callback)
                    bookNames.clear()
                    bookNames.addAll(list)
                    diff.dispatchUpdatesTo(this)
                }
            }
        binding.vPager.apply {
            offscreenPageLimit = 1
            adapter = bookPagerAdapter
        }
        binding.tab.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // TODO scrollToTop()
                }
            })
        }

        TabLayoutMediator(binding.tab, binding.vPager) { tab, pos ->
            tab.text = bookPagerAdapter.bookNames[pos].displayName
        }.attach()

        vm.apply {
            state.launchAndCollectIn(viewLifecycleOwner) {
                bookPagerAdapter.updateBookNames(it.bookNames)
            }
        }
    }
}

private class BookNameDiffUtil(
    private val oldList: List<BookName>,
    private val newList: List<BookName>
) : DiffUtil
.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}
