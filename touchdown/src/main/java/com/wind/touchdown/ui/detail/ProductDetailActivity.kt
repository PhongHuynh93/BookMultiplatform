package com.wind.touchdown.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.book.model.touchdown.Product
import com.wind.touchdown.ui.theme.AppTheme

class ProductDetailActivity : ComponentActivity() {

    companion object {
        const val EXTRA_DATA = "xData"
        const val EXTRA_BUNDLE = "xBundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Surface(color = MaterialTheme.colors.background) {
                        DetailScreen(intent.getBundleExtra(EXTRA_DATA)!!.get(EXTRA_BUNDLE) as Product)
                    }
                }
            }
        }
    }
}
