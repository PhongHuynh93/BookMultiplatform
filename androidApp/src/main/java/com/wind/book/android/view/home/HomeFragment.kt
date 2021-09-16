package com.wind.book.android.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wind.book.android.R
import com.wind.book.android.extension.launchAndCollectIn
import com.wind.book.log
import com.wind.book.viewmodel.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(R.layout.list_view) {
    private val vm: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.apply { 
            bookList.launchAndCollectIn(viewLifecycleOwner) {
                log.e { it.toString() }
            }
        }
    }
}