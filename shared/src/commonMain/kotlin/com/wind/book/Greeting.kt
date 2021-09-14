package com.wind.book

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}