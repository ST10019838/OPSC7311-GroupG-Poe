package com.example.chronometron.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions


object TimeEntriesTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Time Entries"
            val icon = rememberVectorPainter(Icons.Default.Home)

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
        //1.  list of entries
//    var entries by remember(mutableStateOf<List<TimeEntries>>(getTimeEntries()))

//   // 1.1. loop through entries in a list
//      value.foreach {
//          TimesheetListEntry(it)
//      }
//
//    // creation button & daily goal display
        var navigator = LocalNavigator.currentOrThrow


//        Button(onClick = { navigator.push(CustomiserScreen()) }) {
            Text("Entries")
//        }

    }
}