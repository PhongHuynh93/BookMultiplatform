package com.wind.book.sharedUI.view
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.wind.book.sharedUI.normalSpace

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier.testTag("ProgressBarItem")
            .fillMaxWidth()
            .padding(normalSpace)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    )
}
