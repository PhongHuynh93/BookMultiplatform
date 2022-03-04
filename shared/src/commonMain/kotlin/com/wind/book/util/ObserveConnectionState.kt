package com.wind.book.util

import kotlinx.coroutines.flow.Flow

expect class ObserveConnectionState actual constructor(context: AppContext) {
    /**
     * Network Utility to observe availability or unavailability of Internet connection
     */
    fun observeConnectivityAsFlow(): Flow<ConnectionState>
}

sealed class ConnectionState {
    object ConnectionAvailable : ConnectionState()
    object NoConnection : ConnectionState()
}
