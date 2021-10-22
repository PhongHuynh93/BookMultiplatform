package com.wind.book.android.view.podcast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.databinding.PodcastItemBinding
import com.wind.book.android.extension.GetItemCountCallback
import com.wind.book.model.Podcast

class PodcastAdapter(private val callback: Callback) :
    ListAdapter<Podcast, PodcastsViewHolder>(object : DiffUtil.ItemCallback<Podcast>() {
        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem == newItem
        }
    }),
    GetItemCountCallback {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PodcastsViewHolder(
            PodcastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )

    override fun onBindViewHolder(holder: PodcastsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface Callback : PodcastsViewHolder.Callback
}

class PodcastsViewHolder(
    private val binding: PodcastItemBinding,
    private val callback: Callback
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var podcast: Podcast

    init {
        binding.root.setOnClickListener {
            val pos = bindingAdapterPosition
            if (pos >= 0) {
                callback.onClick(podcast)
            }
        }
    }

    fun bind(item: Podcast) {
        podcast = item
        binding.apply {
            podcast = item
        }
    }

    interface Callback {
        fun onClick(podcast: Podcast)
    }
}
