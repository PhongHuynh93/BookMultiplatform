package com.wind.book.data.util

sealed class Failure(message: String) : Exception(message)

object UnknownFailure : Failure("Something went wrong")
object TimeoutFailure : Failure("Connection timed out")
object NoConnectionException : Failure("No connection")
