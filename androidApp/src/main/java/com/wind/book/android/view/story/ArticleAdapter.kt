package com.wind.book.android.view.story

import android.view.LayoutInflater
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
            return oldItem.id == newItem.id
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

    interface Callback : ArticleViewHolder.Callback
}

class ArticleViewHolder(private val binding: ArticleItemBinding, private val rm: RequestManager, val callback: Callback) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var article: Article
    init {
        binding.root.setOnClickListener {
            val pos = bindingAdapterPosition
            if (pos >= 0) {
                callback.onClick(article)
            }
        }
    }

    fun bind(item: Article) {
        this.article = item
        binding.apply {
            this.rm = this@ArticleViewHolder.rm
            article = item
        }
    }

    interface Callback {
        fun onClick(article: Article)
    }
}
