package com.example.chronometron.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.benlu.composeform.formatters.dateShort
import com.example.chronometron.ui.composables.TimeEntryListItem
import com.example.chronometron.ui.viewModels.EntryCreationViewModel
import com.example.chronometron.ui.viewModels.UserSession

@Composable
fun TimeEntriesScreen() {
    var viewModel = viewModel<EntryCreationViewModel>()


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
            items(UserSession.getListOfTimeEntryDates().toList()) {

                var totalDateMinuteDuration by remember { mutableIntStateOf(0) }
                var totalDateHourDuration by remember { mutableIntStateOf(0) }

                // The initial plan was to only loop over the list once, calculating the
                // totals and displaying the records simultaneously, however that did not
                // seem to work, leaving us with having to iterate over the list twice
                it.second.forEach { id ->
                    val entry = UserSession.timeEntries[id]

                    totalDateHourDuration += entry.duration.hours
                    totalDateMinuteDuration += entry.duration.minutes
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(dateShort(it.first), style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${totalDateHourDuration}h ${totalDateMinuteDuration}m",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    it.second.forEach { id ->
                        TimeEntryListItem(entry = UserSession.timeEntries[id])
                    }
                }
            }
        }

        // creation button & daily goal display
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
