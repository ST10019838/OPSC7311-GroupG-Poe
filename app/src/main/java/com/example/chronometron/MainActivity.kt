package com.example.chronometron

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import androidx.room.Room
import cafe.adriel.voyager.navigator.tab.TabNavigator
//import com.example.chronometron.db.LocalDatabase
import com.example.chronometron.ui.composables.ScreenLayout
import com.example.chronometron.ui.tabs.StatisticsTab
import com.example.chronometron.ui.tabs.TimeEntriesTab
import com.example.chronometron.ui.theme.ChronoMetronTheme
//import com.example.chronometron.ui.viewModels.TestViewModel
import com.example.chronometron.ui.viewModels.UserSession
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.HiltAndroidApp
import kotlin.coroutines.coroutineContext

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // use dependency injection
//    private val viewModel by viewModel<ContactViewModel>(
//        factoryProducer = {
//            object: ViewModelProvider.Factory{
//                override fun <T: ViewModel?> create(modelClass: Class<T>): T{
//                    return ContactViewModel(db.dao).as T
//                }
//            }
//        }
//    )

//    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
            ChronoMetronTheme(darkTheme = true) {
                TabNavigator(TimeEntriesTab) { tabNavigator ->
                    ScreenLayout(tabNavigator)
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


