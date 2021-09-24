package com.wind.book.android.view.iab

import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.wind.book.android.R
import com.wind.book.android.databinding.IabBinding
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.android.util.viewBinding
import com.wind.book.viewmodel.iab.IABViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * In app browser
 */
class IABFragment : Fragment(R.layout.iab) {
    private val binding by viewBinding(IabBinding::bind)
    private val vm: IABViewModel by viewModel()
    private val args: IABFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = this@IABFragment.vm
            lifecycleOwner = viewLifecycleOwner
        }
        vm.setIABNav(args.iabNav)
        binding.webView.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    vm.onProgressChanged(newProgress)
                }
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    vm.onPageFinished()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    vm.onReceivedError()
                }
            }
        }
        vm.apply {
            var hasLoadURL = false
            state.launchAndCollectIn(viewLifecycleOwner) {
                binding.progressBar.isVisible = !it.loadDone
                if (!hasLoadURL) {
                    hasLoadURL = true
                    it.url?.let {
                        binding.webView.loadUrl(it)
                    }
                }
            }
        }
    }
}
