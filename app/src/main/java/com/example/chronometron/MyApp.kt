package com.example.chronometron

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger

// import dagger.hilt.android.HiltAndroidApp

/**
 * For providing the application context anywhere.
 */
// @HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // Enable Firebase Realtime Database logging
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)
    }
}
