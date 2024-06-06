package com.example.chronometron.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.benlu.composeform.formatters.dateShort
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.composables.SelectablePeriodSearch
import com.example.chronometron.ui.composables.TimeEntryListItem
import com.example.chronometron.ui.viewModels.UserSession
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@Composable
fun TimeEntriesScreen() {
    var isDialogOpen by remember { mutableStateOf(false) }
    val timeEntries by UserSession.timeEntries.collectAsStateWithLifecycle()
    val datesAndEntries by UserSession.datesAndEntries.collectAsStateWithLifecycle()
    val selectablePeriod by UserSession.selectedPeriod.collectAsStateWithLifecycle()
    var entryToManage: TimeEntry? by remember { mutableStateOf(null) }

    // Main Column layout
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        // Period selection component
        SelectablePeriodSearch(
            value = selectablePeriod,
            onSelectionChange = { UserSession.onSelectedPeriodChange(it?.fromDate, it?.toDate) },
            isOpen = false
        )

        // Display message if no time entries exist
        if (timeEntries.isEmpty()) {
            Text(
                "No Time Entries Created",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else if (datesAndEntries.isEmpty()){
            Text(
                "No time has been recorded during this period",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // LazyColumn for displaying time entries
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            items(datesAndEntries.toList()) {
                val dateHours = it.second.first.hours
                val dateMinutes = it.second.first.minutes

                // Display date and total hours/minutes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(it.first, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    }
                    Text(
                        "${dateHours}h ${dateMinutes}m",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Display individual time entries for the date
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    it.second.second.forEach { id ->
                        val entry = timeEntries[id]
                        TimeEntryListItem(entry = entry, onClick = {
                            entryToManage = entry
                            isDialogOpen = true
                        })
                    }
                }
            }
        }

        // Bottom section with divider, daily goal display, and add button
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

    // Display the time entry management screen if the dialog is open
    if (isDialogOpen) {
        TimeEntryManagementScreen(
            navigationAction = { isDialogOpen = false },
            entryToManage = entryToManage
        )
    }
}

@Composable
private fun DailyGoalDisplay() {
    val datesAndEntries by UserSession.datesAndEntries.collectAsState()
    val minimumDailyGoal by UserSession.minimumGoal.collectAsState()
    val maximumDailyGoal by UserSession.maximumGoal.collectAsState()
    val totalDailyDuration = datesAndEntries[dateShort(Date())]?.first

    val goalToDisplay =
        if ((totalDailyDuration?.hours ?: 0) >= minimumDailyGoal.hours &&
            (totalDailyDuration?.minutes ?: 0) >= minimumDailyGoal.minutes
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
            Text("Daily Goal:", style = MaterialTheme.typography.titleMedium)

            Icon(
                Icons.Default.TaskAlt,
                contentDescription = "Goal Stamp",
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            "${totalDailyDuration?.hours ?: 0}h ${totalDailyDuration?.minutes ?: 0}m / $goalToDisplay",
            style = MaterialTheme.typography.titleLarge
        )
    }
}
