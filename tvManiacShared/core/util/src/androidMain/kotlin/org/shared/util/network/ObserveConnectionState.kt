package org.shared.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.shared.util.AppContext

actual class ObserveConnectionState actual constructor(private val context: AppContext) {

    actual fun observeConnectivityAsFlow(): Flow<ConnectionState> = callbackFlow {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = NetworkCallback { connectionState -> trySend(connectionState) }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        // Set current state
        val currentState = connectivityManager.getCurrentConnectivityState()
        trySend(currentState)

        // Remove callback when not used
        awaitClose {
            // Remove listeners
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    /**
     * Network utility to get current state of internet connection
     */
    val currentConnectivityState: ConnectionState
        get() {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.getCurrentConnectivityState()
        }
}

@Suppress("FunctionName")
fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.ConnectionAvailable)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.NoConnection)
        }
    }
}

private fun ConnectivityManager.getCurrentConnectivityState(): ConnectionState {
    val connected = activeNetwork?.let { network ->
        getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } ?: false

    return if (connected) ConnectionState.ConnectionAvailable else ConnectionState.NoConnection
}
