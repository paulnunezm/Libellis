package com.nunez.libellis

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

interface ConnectivityChecker {
    fun isConected(): Boolean
}

class ConnectivityCheckerImpl(
        val context: Context
) : ConnectivityChecker {

    override fun isConected(): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo:NetworkInfo? = connectivityManager.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }
}
