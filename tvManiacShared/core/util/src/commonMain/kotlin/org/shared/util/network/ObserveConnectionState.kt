package org.shared.util.network

import kotlinx.coroutines.flow.Flow
import org.shared.util.AppContext

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
