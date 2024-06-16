package com.allcarsinone.allcarsinone

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.allcarsinone.allcarsinone.models.retrofit.DatabaseRequests.Companion.getSystemService

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}