package com.wind.touchdown.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wind.touchdown.ui.detail.ProductDetailActivity
import com.wind.touchdown.ui.home.HomeScreen
import com.wind.touchdown.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Surface(color = MaterialTheme.colors.background) {
                        HomeScreen {
                            startActivity(
                                Intent(this, ProductDetailActivity::class.java).apply {
                                    putExtra(
                                        ProductDetailActivity.EXTRA_DATA,
                                        bundleOf(ProductDetailActivity.EXTRA_BUNDLE to it)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
