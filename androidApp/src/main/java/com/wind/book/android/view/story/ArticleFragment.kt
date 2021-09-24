package com.wind.book.android.view.story

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.wind.book.android.R
import com.wind.book.android.databinding.ToolbarListViewBinding
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.util.viewBinding
import com.wind.book.android.view.adapter.LoadingAdapter
import com.wind.book.android.view.home.HomeFragmentDirections
import com.wind.book.model.Article
import com.wind.book.viewmodel.home.StoryViewModel
import com.wind.book.viewmodel.model.IABNav
import org.koin.androidx.viewmodel.ext.android.viewModel
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}
class ArticleFragment : Fragment(R.layout.toolbar_list_view) {
    private val vm: StoryViewModel by viewModel()
    private val binding by viewBinding(ToolbarListViewBinding::bind)

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
        val footerLoadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() {
                vm.retry()
            }
        })

        val concatAdapter =
            ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(), feedAdapter)
        binding.list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                addItemDecoration(ArticleItemDecoration(requireContext()))
            }
            swipeRefresh.apply {
                setOnRefreshListener { vm.retry() }
            }

            loading.retryBtn.setOnClickListener {
                vm.retry()
            }
        }
        vm.apply {
            loadState.launchAndCollectIn(viewLifecycleOwner) { loadState ->
                list.setLoadState(loadState)
                footerLoadingAdapter.loadState = loadState
            }
            data.launchAndCollectIn(viewLifecycleOwner) {
                feedAdapter.submitList(it) {
                    if (it.isNotEmpty() && !concatAdapter.adapters.contains(footerLoadingAdapter)) {
                        concatAdapter.addAdapter(footerLoadingAdapter)
                    }
                }
            }
        }
    }
}