package com.example.chronometron.ui.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccessTime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.chronometron.ui.screens.TimeEntriesScreen
import com.example.chronometron.ui.viewModels.SessionState


object TimeEntriesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Time Entries"
            val icon = rememberVectorPainter(Icons.Sharp.AccessTime)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val datesAndEntries by SessionState.datesAndEntries.collectAsStateWithLifecycle()

        TimeEntriesScreen(datesAndEntries)
    }
}