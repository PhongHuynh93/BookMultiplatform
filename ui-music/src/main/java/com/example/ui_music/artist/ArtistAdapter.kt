package com.example.ui_music.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ui_music.databinding.ItemArtistBinding
import com.wind.book.model.music.Artist
import com.wind.book.viewmodel.music.artist.ArtistEvent

class ArtistAdapter constructor(
    private val callback: ArtistEvent
) : ListAdapter<Artist, ArtistAdapter.ArtistViewHolder>(object : DiffUtil
    .ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            ItemArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            callback
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArtistViewHolder(
        private val binding: ItemArtistBinding,
        private val callback: ArtistEvent
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var artist: Artist

        init {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos >= 0) {
                    callback.onClickArtist(artist)
                }
            }
        }

        fun bind(artist: Artist) {
            this.artist = artist
            binding.apply {
                item = artist
                executePendingBindings()
            }
        }
    }
}
