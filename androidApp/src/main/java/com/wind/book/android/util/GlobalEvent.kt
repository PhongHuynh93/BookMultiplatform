package com.wind.book.android.util

import com.wind.book.model.music.Genre
import kotlinx.coroutines.flow.MutableSharedFlow

val openArtist: MutableSharedFlow<Genre> = MutableSharedFlow()
