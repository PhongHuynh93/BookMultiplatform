package com.wind.book.model

data class Book(
    val id: String,
    val author: String,
    val thumb: Thumb,
    val description: String,
    val title: String,
    val amazonLink: String,
)