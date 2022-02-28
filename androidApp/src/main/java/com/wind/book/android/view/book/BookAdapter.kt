package com.wind.book.android.view.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.databinding.BookItemBinding
import com.wind.book.model.Book
import com.wind.book.viewmodel.book.BookEvent

class BookAdapter(private val bookEvent: BookEvent) :
    ListAdapter<Book, BookViewHolder>(object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }) {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), bookEvent
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class BookViewHolder(private val binding: BookItemBinding, val callback: BookEvent) :
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
            book = item
        }
    }
}
