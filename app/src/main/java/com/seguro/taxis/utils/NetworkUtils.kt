package com.seguro.taxis.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.NonNull
import com.seguro.taxis.manager.Constants.Companion.DOUBLE_POINTS
import com.seguro.taxis.manager.Constants.Companion.ENABLED_NOTIFICATION_LISTENERS

class NetworkUtils {

    companion object {

        fun isConnected(@NonNull context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun openWifiSettings(@NonNull activity: Activity) {
            activity.startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
        }

        fun isNotificationServiceEnabled(activity: Activity): Boolean {
            val pkgName = activity.packageName
            val flat = Settings.Secure.getString(activity.contentResolver, ENABLED_NOTIFICATION_LISTENERS)
            if (!TextUtils.isEmpty(flat)) {
                val names = flat.split(DOUBLE_POINTS.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                for (i in names.indices) {
                    val cn = ComponentName.unflattenFromString(names[i])
                    if (cn != null && TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}
