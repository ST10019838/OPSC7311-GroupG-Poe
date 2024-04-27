package com.example.chronometron

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.chronometron.ui.composables.Layout
import com.example.chronometron.ui.tabs.TimeEntriesTab
import com.example.chronometron.ui.theme.ChronoMetronTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The following if statement was taken from Youtube.com
        // Author: Philipp Lackner
        // Link: https://www.youtube.com/watch?v=12_iKwGIP64

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        setContent {
            ChronoMetronTheme(darkTheme = false) {
//                Navigator(TimeEntriesScreen()) { navigator ->
//
//                    ScreenLayout(navigator = navigator) {
//                        SlideTransition(navigator = navigator)
//                    }
//                }

                TabNavigator(TimeEntriesTab) { tabNavigator ->
                    Layout(tabNavigator)
                }
            }
        }


    }


    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}


// Used to determine if data should be re-fetched
//private data class SessionState(
//
//
//)


