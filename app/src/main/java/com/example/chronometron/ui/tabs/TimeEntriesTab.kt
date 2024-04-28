package com.example.chronometron.ui.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.chronometron.R
import com.example.chronometron.ui.screens.TimeEntriesScreen


object TimeEntriesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Time Entries"
            val iconPainter: Painter = painterResource(id = R.drawable.ic_timeentries_white_24dp)

            return TabOptions(
                index = 1u,
                title = title,
                icon = iconPainter
            )
        }

    @Composable
    override fun Content() {
        TimeEntriesScreen()
    }
}