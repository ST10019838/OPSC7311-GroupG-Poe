package com.chronometron.test

import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object HttpURLConnectionUtil {
    fun isInternetAvailable(): Boolean {
        try {
            val url = URL("https://www.google.com")
            val urlConnect = url.openConnection() as HttpURLConnection
            urlConnect.connectTimeout = 3000
            urlConnect.connect()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}
