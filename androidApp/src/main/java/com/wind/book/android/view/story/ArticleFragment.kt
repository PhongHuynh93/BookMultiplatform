package com.wind.book.android.view.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.common_ui_view.databinding.ToolbarListViewBinding
import com.example.common_ui_view.extension.launchAndCollectIn
import com.example.common_ui_view.extension.safeNavigate
import com.example.common_ui_view.util.tryCast
import com.example.common_ui_view.util.viewBinding
import com.wind.book.android.R
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Article
import com.wind.book.viewmodel.LoadingEvent
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.home.StoryViewModel
import com.wind.book.viewmodel.model.IABNav
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment(R.layout.toolbar_list_view) {
    private val vm: StoryViewModel by viewModel()
    private val binding by viewBinding(ToolbarListViewBinding::bind)
    private val event: LoadingEvent
        get() {
            return vm.event
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title = getString(R.string.story)
            showUpBtn = false
        }
        val list = binding.list

        val articleAdapter = ArticleAdapter(
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

        binding.list.binding.apply {
            rcv.apply {
                adapter = articleAdapter
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
                screen.tryCast<LoadingScreen.Data<Article>> {
                    articleAdapter.submitList(data)
                }
                list.setScreen(screen)
            }
        }
    }
}
