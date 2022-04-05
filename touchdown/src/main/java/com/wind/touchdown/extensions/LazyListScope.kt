package com.wind.touchdown.extensions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

fun <T> LazyListScope.verticalGridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

fun <T> LazyListScope.horizontalGridItems(
    data: List<T>,
    rowCount: Int,
    modifier: Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val columns = if (size == 0) 0 else 1 + (size - 1) / rowCount
    items(columns, key = { it.hashCode() }) { rowIndex ->
        Column(
            verticalArrangement = verticalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until rowCount) {
                val itemIndex = rowIndex * rowCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(modifier = Modifier)
                }
            }
        }
    }
}
