package com.wind.book.model

/**
 * Use the Identifiable protocol to provide a stable notion of identity to a class or value type.
 * For example, you could define a User type with an id property that is stable across your app and your app’s database storage.
 * You could use the id property to identify a particular user even if other data fields change, such as the user’s name.
 * [https://developer.apple.com/documentation/swift/identifiable](Identifiable)
 */
interface Identifiable {
    val id: String
}
