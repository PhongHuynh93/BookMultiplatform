package org.tivi.sharedTivi

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
