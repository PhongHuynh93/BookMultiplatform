package com.wind.book.sharedUI

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

private val smallRadius = 4.dp
private val mediumRadius = 8.dp
private val largeRadius = 12.dp

private val smallCornerShape = RoundedCornerShape(smallRadius)
private val mediumCornerShape = RoundedCornerShape(mediumRadius)
private val largeCornerShape = RoundedCornerShape(largeRadius)

val Shapes = Shapes(
    small = smallCornerShape,
    medium = mediumCornerShape,
    large = largeCornerShape,
)
