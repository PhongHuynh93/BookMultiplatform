package com.example.common_ui_view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.example.common_ui_view.R
import com.example.common_ui_view.binding.bindPaddingBottom
import com.example.common_ui_view.binding.defaultVerList
import com.example.common_ui_view.databinding.ListBinding
import com.example.common_ui_view.extension.getColorAttr
import com.example.common_ui_view.extension.inflater
import org.test.common_model.LoadingScreen

private val TAG = ListView::class.simpleName

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
            binding.rcv.apply {
                defaultVerList()
                bindPaddingBottom()
                hasFixedSize()
            }
        }

        binding.swipeRefresh.apply {
            val arrayOfInt = IntArray(1)
            arrayOfInt[0] = context.getColorAttr(androidx.appcompat.R.attr.colorAccent)
            setColorSchemeColors(*arrayOfInt)
        }
    }

    fun setScreen(screen: LoadingScreen) {
        Log.e(TAG, "screenxxx $screen")
        var isListEmpty = false
        var isLoading = false
        var isError = false
        var isRefresh = false
        // disable swipe when the screen is error/no data/loading
        if (screen is LoadingScreen.Data<*>) {
            binding.swipeRefresh.isEnabled = useSwipe
        } else {
            binding.swipeRefresh.isEnabled = false
        }
        when (screen) {
            is LoadingScreen.Data<*> -> {
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
        binding.loading.loadingContainer.isVisible = isLoading
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
