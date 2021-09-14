package com.wind.book

class Greeting {
    fun greeting(): String {
        return "Hi, ${Platform().platform}!"
    }
}