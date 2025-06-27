package com.spksh.financeapp.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.spksh.financeapp.domain.connectivity.ConnectivityObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Реализация [ConnectivityObserver], отслеживающая состояние сетевого подключения
 */
class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : ConnectivityObserver {

    override fun observe(): Flow<ConnectivityObserver.Status> = callbackFlow {
        val cm = context.getSystemService(ConnectivityManager::class.java)!!
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val callback = createCallback(this)

        cm.registerNetworkCallback(request, callback)
        val active = cm.activeNetwork?.let { net ->
            cm.getNetworkCapabilities(net)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
        trySend(if (active) ConnectivityObserver.Status.Available else ConnectivityObserver.Status.Unavailable)

        awaitClose { cm.unregisterNetworkCallback(callback) }
    }

    private fun createCallback(
        scope: ProducerScope<ConnectivityObserver.Status>
    ): ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                scope.trySend(ConnectivityObserver.Status.Available)
            }
            override fun onLost(network: Network) {
                scope.trySend(ConnectivityObserver.Status.Lost)
            }
            override fun onLosing(network: Network, maxMsToLive: Int) {
                scope.trySend(ConnectivityObserver.Status.Losing)
            }
            override fun onUnavailable() {
                scope.trySend(ConnectivityObserver.Status.Unavailable)
            }
        }
}