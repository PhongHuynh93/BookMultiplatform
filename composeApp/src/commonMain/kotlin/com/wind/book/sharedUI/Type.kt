package com.wind.book.sharedUI

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

private val Inter = FontFamily(
    _font("inter_300.ttf", FontWeight.Light),
    _font("inter_400.ttf", FontWeight.Normal),
    _font("inter_500.ttf", FontWeight.Medium),
    _font("inter_700.ttf", FontWeight.Bold)
)

val CocaTypography = Typography(defaultFontFamily = Inter)
