package com.wind.book.android.view.podcast_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui_view.adapter.LoadingAdapter
import com.example.common_ui_view.binding.setLayoutScrollBehavior
import com.example.common_ui_view.binding.showUpBtn
import com.example.common_ui_view.databinding.ToolbarListViewBinding
import com.example.common_ui_view.extension.handleLoadMore
import com.example.common_ui_view.extension.launchAndCollectIn
import com.example.common_ui_view.util.viewBinding
import com.google.android.material.appbar.AppBarLayout
import com.wind.book.model.Episode
import com.wind.book.model.Podcast
import com.wind.book.viewmodel.LoadingScreen
import com.wind.book.viewmodel.podcast_detail.PodcastDetailEffect
import com.wind.book.viewmodel.podcast_detail.PodcastDetailEvent
import com.wind.book.viewmodel.podcast_detail.PodcastDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PodcastDetailFragment : Fragment(com.example.common_ui_view.R.layout.toolbar_list_view) {
    private val binding by viewBinding(ToolbarListViewBinding::bind)
    private val vm: PodcastDetailViewModel by viewModel()
    private val event: PodcastDetailEvent
        get() {
            return vm.event
        }

    private val args: PodcastDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = ""
        binding.toolbar.apply {
            showUpBtn(true)
            val flag =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            setLayoutScrollBehavior(flag)
            ViewCompat.setElevation(this, 0f)
        }
        val list = binding.list

        val podcastAdapter = PodcastInfoAdapter(
            object : PodcastInfoAdapter.Callback {
                override fun onShareClick(podcast: Podcast) = event.onShare(podcast)

                override fun onOpenLinkClick(podcast: Podcast) = event.onOpenLink(podcast)

                override fun onSortClick() = Unit
            }
        )

        val episodesAdapter = EpisodesAdapter(object : EpisodesAdapter.Callback {
            override fun onClick(episode: Episode) = Unit
        })

        val loadingAdapter = LoadingAdapter(object : LoadingAdapter.Callback {
            override fun onClickRetryBtn() = event.retry()
        })

        val concatAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
            podcastAdapter,
            episodesAdapter
        )
        list.binding.apply {
            rcv.apply {
                adapter = concatAdapter
                addItemDecoration(PodcastItemDecoration(context))
                handleLoadMore {
                    event.loadData(it)
                }
                val toolbarElevation =
                    resources.getDimension(com.example.common_ui_view.R.dimen.toolbar_elevation)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val hide =
                            recyclerView.computeVerticalScrollOffset() < binding.toolbar.height
                        binding.toolbar.title = if (hide) "" else title
                        ViewCompat.setElevation(binding.appBar, if (hide) 0f else toolbarElevation)
                    }
                })
                overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
            list.setOnRetryClick { event.retry() }
            swipeRefresh.apply {
                setOnRefreshListener { event.refresh() }
            }
        }
        args.podcast.also { podcast ->
            vm.setArgs(podcast)
            title = podcast.title
            podcastAdapter.podcast = podcast
        }
        vm.apply {
            state.launchAndCollectIn(viewLifecycleOwner) {
                val screen = it.screen
                if (screen is LoadingScreen.Data<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val data = screen.data as List<Episode>
                    episodesAdapter.submitList(data) {
                        if (data.isNotEmpty() && !concatAdapter.adapters.contains(
                                loadingAdapter
                            )
                        ) {
                            concatAdapter.addAdapter(loadingAdapter)
                        }
                    }
                }
                list.setScreen(screen)
                loadingAdapter.loadState = screen
            }
            effect.launchAndCollectIn(viewLifecycleOwner) {
                when (it) {
                    is PodcastDetailEffect.OpenLink -> {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(it.podcast.listennotesUrl)
                            )
                        )
                    }
                    is PodcastDetailEffect.Share -> {
                        startActivity(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, it.podcast.listennotesUrl)
                            }
                        )
                    }
                }
            }
        }
    }
}
