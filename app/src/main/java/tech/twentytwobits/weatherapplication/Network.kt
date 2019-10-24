package tech.twentytwobits.weatherapplication

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

/*
    Verifica si existe conectividad de red y acceso a Internet
 */
class Network {
    companion object {
        fun verifyConnection(activity: AppCompatActivity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }
    }
}