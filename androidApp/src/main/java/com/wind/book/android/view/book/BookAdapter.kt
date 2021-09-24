package com.wind.book.android.view.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.wind.book.android.databinding.BookItemBinding
import com.wind.book.android.extension.GetItemCountCallback
import com.wind.book.model.Book
import com.wind.book.viewmodel.home.BookEvent

class BookAdapter(private val rm: RequestManager, private val bookEvent: BookEvent) :
    ListAdapter<Book, BookViewHolder>(object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }),
    GetItemCountCallback {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), rm, bookEvent
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class BookViewHolder(private val binding: BookItemBinding, private val rm: RequestManager, val callback: BookEvent) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var book: Book

    init {
        binding.buyBtn.setOnClickListener {
            val pos = bindingAdapterPosition
            if (pos >= 0) {
                callback.onClickBuy(book)
            }
        }
    }

    fun bind(item: Book) {
        this.book = item
        binding.apply {
            this.rm = this@BookViewHolder.rm
            book = item
        }
    }
}
