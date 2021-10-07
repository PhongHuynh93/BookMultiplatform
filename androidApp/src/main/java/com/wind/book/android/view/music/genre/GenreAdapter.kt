package com.wind.book.android.view.music.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.wind.book.android.databinding.ItemGenreBinding
import com.wind.book.log
import com.wind.book.model.music.Genre
import com.wind.book.viewmodel.music.genre.GenreEvent

class GenreAdapter(
    private val requestManager: RequestManager,
    private val event: GenreEvent
) : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(object : DiffUtil
    .ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            binding = ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            requestManager = requestManager,
            callback = event
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GenreViewHolder(
        private val binding: ItemGenreBinding,
        private val requestManager: RequestManager,
        private val callback: GenreEvent
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var genre: Genre

        init {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos >= 0) {
                    callback.onClickGenre(genre)
                }
            }
        }

        fun bind(genre: Genre) {
            this.genre = genre
            log.d { "request mana $requestManager" }
            binding.apply {
                rm = requestManager
                item = genre
                executePendingBindings()
            }
        }
    }
}
