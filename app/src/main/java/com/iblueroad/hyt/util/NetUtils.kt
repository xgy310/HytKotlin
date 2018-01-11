package com.iblueroad.hyt.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * <pre>
 *     author : SkyXiao
 *     e-mail : 532370487@qq.com
 *     time   : 2017/12/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
object NetUtils {
    fun isAvailable(): Boolean {
        val context = AUtils.appContext
        if (context != null) {
            val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    fun isConnected(): Boolean {
        val context = AUtils.appContext
        var status = false
        try {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var netInfo: NetworkInfo? = cm.getNetworkInfo(0)
            if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) {
                status = true
            } else {
                netInfo = cm.getNetworkInfo(1)
                if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED)
                    status = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return status
    }
}