package org.shared.util

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}