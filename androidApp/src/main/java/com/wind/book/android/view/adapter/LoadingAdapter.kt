package com.wind.book.android.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.R
import com.wind.book.android.databinding.LoadMoreItemBinding
import com.wind.book.log
import com.wind.book.viewmodel.LoadingScreen

private val TAG = LoadingAdapter::class.simpleName

class LoadingAdapter(private val callback: Callback) :
    RecyclerView.Adapter<LoadingAdapter.LoadStateViewHolder>() {

    /**
     * LoadState to present in the adapter.
     *
     * Changing this property will immediately notify the Adapter to change the item it's
     * presenting.
     */
    var loadState: LoadingScreen? = null
        set(loadState) {
            log.v { "$TAG screen=$loadState field=$field" }
            if (field != loadState) {
                val displayOldItem = displayLoadStateAsItem(field)
                val displayNewItem = displayLoadStateAsItem(loadState)

                if (displayOldItem && !displayNewItem) {
                    notifyItemRemoved(0)
                } else if (displayNewItem && !displayOldItem) {
                    notifyItemInserted(0)
                } else if (displayOldItem && displayNewItem) {
                    notifyItemChanged(0)
                }
                field = loadState
            }
        }

    /**
     * Returns true if the LoadState should be displayed as a list item when active.
     */
    private fun displayLoadStateAsItem(loadState: LoadingScreen?): Boolean {
        return when (loadState) {
            is LoadingScreen.Data<*> -> !loadState.isEndPage
            is LoadingScreen.Error -> true
            else -> false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.load_state_item
    }

    override fun getItemCount(): Int = (if (displayLoadStateAsItem(loadState)) 1 else 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LoadStateViewHolder(
            LoadMoreItemBinding.inflate(inflater, parent, false)
        ).also { vh ->
            vh.binding.retryBtn.setOnClickListener {
                val pos = vh.bindingAdapterPosition
                if (pos >= 0) {
                    callback.onClickRetryBtn()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, position: Int) {
        holder.bind(loadState!!)
    }

    interface Callback {
        fun onClickRetryBtn()
    }

    class LoadStateViewHolder(val binding: LoadMoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(screen: LoadingScreen) {
            var isError = false
            var isEndOfPage = false
            if (screen is LoadingScreen.Error) {
                isError = true
                binding.errorTv.text = screen.errorMessage
            } else if (screen is LoadingScreen.Data<*>) {
                isEndOfPage = screen.isEndPage
            }

            binding.progressBar.isVisible = !isEndOfPage && !isError
            binding.retryBtn.isVisible = isError
            binding.errorTv.isVisible = isError
        }
    }
}
