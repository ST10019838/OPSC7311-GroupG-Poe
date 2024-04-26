package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun TimeEntriesScreen() {
    //1.  list of entries
//    var entries by remember(mutableStateOf<List<TimeEntries>>(getTimeEntries()))

//   // 1.1. loop through entries in a list
//      value.foreach {
//          TimesheetListEntry(it)
//      }
//
//    // creation button & daily goal display
    val navigator = LocalNavigator.currentOrThrow
    var openDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { openDialog = true }
        ) {
            Text("Go forward")
        }
    }

    if (openDialog) {
        TimeEntriesCreationScreen(navigationAction = { openDialog = false }, action = {})
    }

}
