package com.wind.book.android.view.podcast_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.databinding.EpisodeItemBinding
import com.wind.book.model.Episode

class EpisodesAdapter(private val callback: Callback) :
    ListAdapter<Episode, EpisodeViewHolder>(object : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }) {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodeViewHolder(
            EpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface Callback : EpisodeViewHolder.Callback
}

class EpisodeViewHolder(
    private val binding: EpisodeItemBinding,
    val callback: Callback
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var episode: Episode

    init {
        binding.root.setOnClickListener {
            val pos = bindingAdapterPosition
            if (pos >= 0) {
                callback.onClick(episode)
            }
        }
    }

    fun bind(item: Episode) {
        episode = item
        binding.episode = item
    }

    interface Callback {
        fun onClick(episode: Episode)
    }
}
