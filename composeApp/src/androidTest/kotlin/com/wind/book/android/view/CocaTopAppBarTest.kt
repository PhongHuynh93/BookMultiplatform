package com.wind.book.android.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.wind.book.sharedUI.PreviewAppTheme
import org.junit.Rule
import org.junit.Test

class CocaTopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_title_should_be_displayed_in_home_app_bar() {
        composeTestRule.setContent {
            PreviewAppTheme {
                CocaTopAppBar(title = "Title")
            }
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    }
}
