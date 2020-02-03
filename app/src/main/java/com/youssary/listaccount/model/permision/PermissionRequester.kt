package com.youssary.listaccount.model.permision

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener


@Suppress("DEPRECATION")
class PermissionRequester(private val activity: Activity, private val permission: String) {

    fun request(continuation: (Boolean) -> Unit) {
        if (conect())
            Dexter
                .withActivity(activity)
                .withPermission(permission)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation(false)
                    }
                }
                ).check()
    }

    fun conect(): Boolean {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}