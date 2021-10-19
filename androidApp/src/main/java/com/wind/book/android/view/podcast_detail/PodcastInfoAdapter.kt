package com.wind.book.android.view.podcast_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wind.book.android.R
import com.wind.book.android.databinding.PodcastDetailItemBinding
import com.wind.book.model.Podcast

class PodcastInfoAdapter(
    private val callback: Callback
) :
    RecyclerView.Adapter<PodcastViewHolder>() {

    var podcast: Podcast? = null
        set(value) {
            if (!isSame(field, value)) {
                val displayOldItem = field
                if (value == null && displayOldItem != null) {
                    notifyItemRemoved(0)
                } else if (value != null && displayOldItem == null) {
                    notifyItemInserted(0)
                } else if (value != null && displayOldItem != null) {
                    notifyItemChanged(0)
                }
                field = value
            }
        }

    private fun isSame(oldItem: Podcast?, newItem: Podcast?): Boolean {
        if (oldItem == null && newItem == null)
            return true
        if (oldItem != null && newItem != null) {
            return oldItem.id == newItem.id &&
                oldItem.title == newItem.title &&
                oldItem.publisher == newItem.publisher &&
                oldItem.thumbnail == newItem.thumbnail &&
                oldItem.totalEpisodes == newItem.totalEpisodes &&
                oldItem.description == newItem.description
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PodcastViewHolder(
            PodcastDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        podcast?.let { holder.bind(it) }
    }

    override fun getItemCount() = if (podcast == null) 0 else 1

    override fun getItemViewType(position: Int) = R.layout.podcast_detail_item

    interface Callback : PodcastViewHolder.Callback
}

class PodcastViewHolder(
    private val binding: PodcastDetailItemBinding,
    val callback: Callback
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var podcast: Podcast

    init {
        binding.sortBtn.setOnClickListener {
            callback.onSortClick()
        }
        binding.shareBtn.setOnClickListener {
            callback.onShareClick(podcast)
        }
        binding.iabBtn.setOnClickListener {
            callback.onOpenLinkClick(podcast)
        }
    }

    fun bind(item: Podcast) {
        podcast = item
        binding.apply {
            podcast = item
        }
    }

    interface Callback {
        fun onShareClick(podcast: Podcast)

        fun onOpenLinkClick(podcast: Podcast)

        fun onSortClick()
    }
}
