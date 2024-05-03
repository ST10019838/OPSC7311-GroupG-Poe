package com.example.chronometron.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.chronometron.ui.composables.ScreenLayout
import com.example.chronometron.ui.tabs.TimeEntriesTab

class LandingScreen() : Screen {
    @Composable
    override fun Content() {
        TabNavigator(TimeEntriesTab) { tabNavigator ->
            ScreenLayout(tabNavigator)
        }
    }
}