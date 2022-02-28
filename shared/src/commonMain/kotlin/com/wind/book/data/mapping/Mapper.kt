package com.wind.book.data.mapping

fun interface Mapper<in T, out R> {
    fun apply(input: T): R
}
