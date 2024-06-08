package com.example.chronometron.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.benlu.composeform.formatters.dateShort
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.composables.SelectablePeriodSearch
import com.example.chronometron.ui.composables.TimeEntryListItem
import com.example.chronometron.ui.viewModels.SessionState
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@Composable
fun TimeEntriesScreen(
    datesAndEntries: Map<String, Pair<FirebaseHours, MutableList<Int>>>,
    usingArchive: Boolean = false
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val timeEntries by if (usingArchive) SessionState.archivedTimeEntries.collectAsStateWithLifecycle()
    else SessionState.timeEntries.collectAsStateWithLifecycle()

    val selectablePeriod by SessionState.selectedPeriod.collectAsStateWithLifecycle()
    var entryToManage: TimeEntry? by remember { mutableStateOf(null) }


    // Needs to be a column
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        SelectablePeriodSearch(
            value = selectablePeriod,
            onSelectionChange = { SessionState.onSelectedPeriodChange(it?.fromDate, it?.toDate) },
            isOpen = false
        )

        if (timeEntries.isEmpty()) {
            Text(
                if (usingArchive) "No Time Entries Archived" else "No Time Entries Created",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else if (datesAndEntries.isEmpty()) {
            Text(
                "No time has been recorded during this period",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(datesAndEntries.toList()) {

//                var totalDateMinuteDuration = 0
//                var totalDateHourDuration = 0

                // The initial plan was to only loop over the list once, calculating the
                // totals and displaying the records simultaneously, however that did not
                // seem to work, leaving us with having to iterate over the list twice
//                it.second.forEach { id ->
//                    val entry = timeEntries[id]
//
//                    totalDateHourDuration += entry.duration.hours
//                    totalDateMinuteDuration += entry.duration.minutes
//                }
                val dateHours = it.second.first.hours
                val dateMinutes = it.second.first.minutes

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
//                        val dateDuration =
//                            UserSession.getTotalDailyDuration(formattedDate = it.first)

                        Text(
                            it.first, style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                        // Make a util function
                        // For goal adherence
//                        if (dateDuration.hours >= UserSession.minimumGoal.hours &&
//                            dateDuration.minutes >= UserSession.minimumGoal.minutes
//                        ) {
//                            Icon(
//                                Icons.Default.TaskAlt,
//                                contentDescription = "Goal Stamp",
//                                modifier = Modifier.size(20.dp)
//                            )
//                        }
                    }

                    Text(
                        "${dateHours}h ${dateMinutes}m",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

//                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                it.second.second.first {
//
//                    }

                it.second.second.forEach { id ->
                    val entry = timeEntries[id]
                    // the following function was adapted from stackoverflow
                    // Author: chuckj
                    // Link: https://stackoverflow.com/questions/70186437/use-item-keys-in-non-lazy-column
                    key(entry.id){
                        TimeEntryListItem(entry = entry, onClick = {
                            entryToManage = entry
                            isDialogOpen = true
                        })
                    }

                }
//                }
            }
        }

        // creation button & daily goal display
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(100))
                    .align(Alignment.CenterHorizontally),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DailyGoalDisplay()

                FloatingActionButton(
                    onClick = {
                        entryToManage = null
                        isDialogOpen = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }

            }
        }

    }


    if (isDialogOpen) {
        TimeEntryManagementScreen(
            navigationAction = { isDialogOpen = false },
            entryToManage = entryToManage
        )
    }
}

@Composable
private fun DailyGoalDisplay() {
    val datesAndEntries by SessionState.datesAndEntries.collectAsState()
    val minimumDailyGoal by SessionState.minimumGoal.collectAsState()
    val maximumDailyGoal by SessionState.maximumGoal.collectAsState()
    val totalDailyDuration = datesAndEntries[dateShort(Date())]?.first

    var goalToDisplay =
        if ((totalDailyDuration?.hours ?: 0) >= minimumDailyGoal.hours!! &&
            (totalDailyDuration?.minutes ?: 0) >= minimumDailyGoal.minutes!!
        ) {
            "${maximumDailyGoal.hours}h ${maximumDailyGoal.minutes}m"
        } else {
            "${minimumDailyGoal.hours}h ${minimumDailyGoal.minutes}m"
        }

    Column(
        modifier = Modifier.width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Daily Goal:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Icon(
                Icons.Default.TaskAlt,
                contentDescription = "Goal Stamp",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            "${totalDailyDuration?.hours ?: 0}h ${totalDailyDuration?.minutes ?: 0}m / $goalToDisplay",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )


    }
}


