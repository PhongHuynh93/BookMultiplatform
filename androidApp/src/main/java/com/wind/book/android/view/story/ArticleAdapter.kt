package com.wind.book.android.view.story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.wind.book.android.databinding.ArticleItemBinding
import com.wind.book.android.extension.GetItemCountCallback
import com.wind.book.model.Article

class ArticleAdapter(private val rm: RequestManager, private val callback: Callback) :
    ListAdapter<Article, ArticleViewHolder>(object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }),
    GetItemCountCallback {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), rm, callback
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface Callback : ArticleViewHolder.Callback {
        fun onClick(view: View, pos: Int, item: Article)
    }
}

class ArticleViewHolder(private val binding: ArticleItemBinding, private val rm: RequestManager, val callback: Callback) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Article) {
        binding.apply {
            this.rm = this@ArticleViewHolder.rm
            article = item
        }
        binding.root.setOnClickListener {
            if (callback is ArticleAdapter.Callback) {
                callback.onClick(binding.root, 0, item)
            }
        }
    }

    interface Callback
}
