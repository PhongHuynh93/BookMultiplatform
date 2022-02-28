package com.wind.book.desktopApp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import com.wind.book.desktopApp.view.music.GenreScreen
import com.wind.book.initKoin
import com.wind.book.sharedUI.AppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.koin.dsl.module

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalComposeUiApi
fun main() = application {
    initKoin(
        module {
            // don't need any things specific to jvm
        }
    )

    val backEvents = MutableSharedFlow<Boolean>()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Book",
        state = rememberWindowState(width = 400.dp, height = 800.dp),
        onKeyEvent = { event ->
            if (event.key == Key.Escape && event.type == KeyEventType.KeyUp) {
                GlobalScope.launch { backEvents.emit(true) }
                true
            } else false
        }
    ) {
        AppTheme {
            Navigator(GenreScreen())
        }
    }
}
