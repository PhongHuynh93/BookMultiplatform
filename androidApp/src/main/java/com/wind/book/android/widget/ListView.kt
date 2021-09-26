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
import com.wind.book.viewmodel.LoadingScreen

private val TAG = ListView::class.java.simpleName

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

    fun setScreen(screen: LoadingScreen) {
        log.e { "$TAG screenxxx $screen" }
        var isListEmpty = false
        var isLoading = false
        var isError = false
        var isRefresh = false
        // disable swipe when not have data
        if (screen is LoadingScreen.Data<*>) {
            binding.swipeRefresh.isEnabled = useSwipe
        } else {
            binding.swipeRefresh.isEnabled = false
        }
        when (screen) {
            is LoadingScreen.Data<*> -> {
                // enable swipe when have data
                binding.swipeRefresh.isEnabled = useSwipe
                isRefresh = screen.isRefresh
            }
            is LoadingScreen.Error -> {
                isError = true
                binding.loading.errorTv.text = screen.errorMessage
            }
            LoadingScreen.Loading -> {
                isLoading = true
            }
            is LoadingScreen.NoData -> {
                isListEmpty = true
            }
        }
        // show empty list
        showEmptyList(isListEmpty)
        // Show loading spinner during initial load or refresh.
        binding.loading.loadingContainer.apply {
            if (isLoading) {
                fadeIn()
            } else {
                fadeOut()
            }
        }
        // refresh
        binding.swipeRefresh.isRefreshing = isRefresh
        // Show the retry state if initial load or refresh fails and there are no items.
        binding.loading.retryBtn.isVisible = isError
        binding.loading.errorTv.isVisible = isError
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

    fun setOnRetryClick(onRetry: () -> Unit) {
        binding.loading.retryBtn.setOnClickListener {
            onRetry()
        }
    }
}
