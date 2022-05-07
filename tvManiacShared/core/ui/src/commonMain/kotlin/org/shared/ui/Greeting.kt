package org.shared.ui

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}