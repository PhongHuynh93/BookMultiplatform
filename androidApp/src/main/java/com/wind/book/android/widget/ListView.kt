package com.wind.book.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.wind.book.android.R
import com.wind.book.android.binding.bindPaddingBottom
import com.wind.book.android.binding.defaultVerList
import com.wind.book.android.databinding.ListBinding
import com.wind.book.android.extension.fadeIn
import com.wind.book.android.extension.fadeOut
import com.wind.book.android.extension.getColorAttr
import com.wind.book.android.extension.inflater
import com.wind.book.log
import com.wind.book.viewmodel.util.LoadState

class ListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var useSwipe: Boolean = false
    val binding by lazy {
        ListBinding.bind(this)
    }

    init {
        context.inflater.inflate(R.layout.list, this, true)

        context.obtainStyledAttributes(attrs, R.styleable.ListView).use { a ->
            val enableSwipe = a.getBoolean(R.styleable.ListView_lv_enableSwipe, false)
            useSwipe = enableSwipe
            binding.swipeRefresh.isEnabled = enableSwipe
        }

        binding.rcv.apply {
            defaultVerList()
            bindPaddingBottom()
        }
        binding.swipeRefresh.apply {
            val arrayOfInt = IntArray(1)
            arrayOfInt[0] = context.getColorAttr(R.attr.colorAccent)
            setColorSchemeColors(*arrayOfInt)
        }
    }

    fun setLoadState(loadState: LoadState) {
        log.e { "load statexxx $loadState" }
        // show empty list
        val isListEmpty = loadState is LoadState.NotLoading.Complete && loadState.isEmpty
        showEmptyList(isListEmpty)
        // Show loading spinner during initial load or refresh.
        val isLoading = loadState is LoadState.Loading && loadState.isEmpty
        binding.loading.loadingContainer.apply {
            if (isLoading) {
                fadeIn()
            } else {
                fadeOut()
            }
        }
        if (loadState.isEmpty) {
            // disable swipe when list is empty
            binding.swipeRefresh.isEnabled = false
        } else {
            binding.swipeRefresh.isEnabled = useSwipe
        }
        // Show the retry state if initial load or refresh fails and there are no items.
        binding.loading.retryBtn.isVisible = loadState is LoadState.Error && loadState.isEmpty
        showError(loadState)
    }

    private fun showError(loadState: LoadState) {
        val errorState = loadState as? LoadState.Error
        binding.loading.errorTv.isVisible = errorState != null && loadState.isEmpty
        errorState?.let {
            binding.loading.errorTv.text = it.error.toString()
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyListContainer.visibility = View.VISIBLE
            binding.rcv.visibility = View.GONE
        } else {
            binding.emptyListContainer.visibility = View.GONE
            binding.rcv.visibility = View.VISIBLE
        }
    }

    fun setRefreshState(isOn: Boolean) {
        log.e { "setRefreshStatexx $isOn" }
        binding.swipeRefresh.isRefreshing = isOn
    }
}
