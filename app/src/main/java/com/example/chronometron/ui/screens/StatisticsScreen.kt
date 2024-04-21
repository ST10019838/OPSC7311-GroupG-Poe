package com.example.chronometron.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.R
import com.example.chronometron.ui.composables.ScreenLayout


object StatisticsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Statistics"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
//    // Category hours tab
//
//    // Daily hours tab
//        ScreenLayout(displayFAB = true) {
            Text("Stats Screen")
//        }
    }
}