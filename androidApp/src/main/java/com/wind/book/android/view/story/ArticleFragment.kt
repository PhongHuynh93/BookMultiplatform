package com.wind.book.android.view.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.extension.safeNavigate
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Article
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.home.StoryEvent
import com.wind.book.viewmodel.home.StoryViewModel
import com.wind.book.viewmodel.model.IABNav
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment(R.layout.toolbar_list_view) {
    private val vm: StoryViewModel by viewModel()
    private val binding by viewBinding(ToolbarListViewBinding::bind)
    private val event: StoryEvent
        get() {
            return vm.event
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.title_story)
        val list = binding.list

        val feedAdapter = ArticleAdapter(
            Glide.with(this),
            object : ArticleAdapter.Callback {
                override fun onClick(article: Article) {
                    findNavController().safeNavigate(
                        HomeFragmentDirections.actionArticleFragmentToIABFragment(
                            IABNav(
                                title = article.title,
                                url = article.url
                            )
                        )
                    )
                }
            }
        )

        val concatAdapter =
            ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(), feedAdapter)
        binding.list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                addItemDecoration(ArticleItemDecoration(requireContext()))
            }
            swipeRefresh.apply {
                setOnRefreshListener { event.refresh() }
            }

            loading.retryBtn.setOnClickListener {
                event.retry()
            }
        }
        vm.apply {
            state.launchAndCollectIn(viewLifecycleOwner) {
                val screen = it.screen
                if (screen is LoadingScreen.Data<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val data = screen.data as List<Article>
                    feedAdapter.submitList(data)
                }
                list.setScreen(screen)
            }
        }
    }
}
