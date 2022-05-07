package org.shared.persistence

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}