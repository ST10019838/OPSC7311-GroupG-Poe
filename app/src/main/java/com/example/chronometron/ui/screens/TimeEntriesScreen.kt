package com.example.chronometron.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chronometron.ui.viewModels.EntryCreationViewModel

@Composable
fun TimeEntriesScreen() {
    //1.  list of entries
//    var entries by remember(mutableStateOf<List<TimeEntries>>(getTimeEntries()))
    var entries by remember { mutableStateOf(listOf("1", "2")) }


//   // 1.1. loop through entries in a list
//      value.foreach {
//          TimesheetListEntry(it)
//      }
//
//    // creation button & daily goal display
//    var openDialog by remember { mutableStateOf(false) }
    var viewModel = viewModel<EntryCreationViewModel>()


    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            items(entries) { entry ->
                Text(entry)
            }
        }

        FloatingActionButton(
            onClick = { viewModel.isDialogOpen = true },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }


    if (viewModel.isDialogOpen) {
        TimeEntriesCreationScreen(navigationAction = { viewModel.isDialogOpen = false },
            onCreate = {})
    }


}
