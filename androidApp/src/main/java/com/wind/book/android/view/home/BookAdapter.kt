package com.wind.book.android.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.wind.book.android.databinding.BookItemBinding
import com.wind.book.android.extension.GetItemCountCallback
import com.wind.book.model.Book

class BookAdapter(private val rm: RequestManager, private val callback: Callback) :
    ListAdapter<Book, BookAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }), GetItemCountCallback {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener { view ->
                val pos = bindingAdapterPosition
                if (pos >= 0) {
                    getItem(pos)?.let {
                        callback.onClick(view, pos, it)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface Callback {
        fun onClick(view: View, pos: Int, item: Book)
    }

    inner class ViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
        }
    }
}