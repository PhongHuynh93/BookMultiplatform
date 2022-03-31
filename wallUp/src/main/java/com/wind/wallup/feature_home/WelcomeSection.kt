package com.wind.wallup.feature_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wind.wallup.ui.theme.PreviewAppTheme

fun LazyListScope.welcomeSection() {
    item(key = "welcome_section") { WelcomeSection() }
}

@Composable
private fun WelcomeSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Welcome to WallUp",
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onBackground
                )
            )
            Text(
                text = "Discover Unsplash photos and find best wallpaper for you.",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onBackground
                )
            )
        }
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = {
//            onEvent(
//                HomeEvent.Navigate(BookmarkScreenDestination)
//            )
            }
        ) {
            Icon(
                imageVector = Icons.Default.Bookmarks,
                contentDescription = "Bookmarks",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview
@Composable
fun WelcomeSectionPreview() {
    PreviewAppTheme {
        LazyColumn {
            welcomeSection()
        }
    }
}
