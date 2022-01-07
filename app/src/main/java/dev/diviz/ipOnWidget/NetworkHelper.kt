package dev.diviz.ipOnWidget

import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

class NetworkHelper {

    fun getListNetworkInterfeces(): List<IpInfo> {
        val result = mutableListOf<IpInfo>()
        try {
            for (networkInterface in NetworkInterface.getNetworkInterfaces()) {
                for (inetAddress in networkInterface.inetAddresses) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        result.add(IpInfo(networkInterface.displayName, inetAddress.hostAddress ?: ""))
                    }
                }
            }
        } catch (ex: SocketException) {
            Log.e("TAG", ex.message ?: "")
        } catch (ex: Exception) {
            Log.e("TAG", ex.message ?: "")
        }

        return result.filter { it.ipAddress.isNotEmpty() }
    }

    fun getStringInfo(): String = getListNetworkInterfeces().joinToString { it.name + ": " + it.ipAddress }

}