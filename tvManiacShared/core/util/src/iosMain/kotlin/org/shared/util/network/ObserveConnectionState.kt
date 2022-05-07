package org.shared.util.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.shared.util.AppContext

actual class ObserveConnectionState actual constructor(context: AppContext) {
    actual fun observeConnectivityAsFlow(): Flow<ConnectionState> {
        // TODO:: ADD Connection State implementation.
        return flowOf(ConnectionState.ConnectionAvailable)
    }
}
