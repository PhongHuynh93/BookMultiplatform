package com.wind.book.model

data class Book(
    override val id: String,
    val author: String,
    val thumb: Thumb,
    val description: String,
    val title: String,
    val amazonLink: String,
) : Identifiable
