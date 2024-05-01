package com.example.chronometron.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chronometron.ui.composables.SelectablePeriodSearch
import com.example.chronometron.ui.composables.TimeEntryListItem
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.ui.viewModels.UserSession.onSelectedPeriodChange

@Composable
fun TimeEntriesScreen() {
    var isDialogOpen by remember { mutableStateOf(false) }
    val datesAndEntries by UserSession.datesAndEntries.collectAsState()
    val selectablePeriod by UserSession.selectedPeriod.collectAsState()

    // Needs to be a column
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        SelectablePeriodSearch(
            value = selectablePeriod,
            onSelectionChange = { onSelectedPeriodChange(it?.fromDate, it?.toDate) },
            isOpen = false
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            items(datesAndEntries.toList()) {

                var totalDateMinuteDuration = 0
                var totalDateHourDuration = 0

                // The initial plan was to only loop over the list once, calculating the
                // totals and displaying the records simultaneously, however that did not
                // seem to work, leaving us with having to iterate over the list twice
                it.second.forEach { id ->
                    val entry = UserSession.timeEntries.value[id]

                    totalDateHourDuration += entry.duration.hours
                    totalDateMinuteDuration += entry.duration.minutes
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        val dateDuration =
                            UserSession.getTotalDailyDuration(formattedDate = it.first)

                        Text(it.first, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                        // Make a util function
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
                        "${totalDateHourDuration}h ${totalDateMinuteDuration}m",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    it.second.forEach { id ->
                        TimeEntryListItem(entry = UserSession.timeEntries.value[id])
                    }
                }
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
                thickness = 2.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DailyGoalDisplay()

                FloatingActionButton(
                    onClick = { isDialogOpen = true },
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }


            }
        }

    }


    if (isDialogOpen) {
        TimeEntriesCreationScreen(
            navigationAction = { isDialogOpen = false },
            onCreate = {})
    }
}

@Composable
private fun DailyGoalDisplay() {
    val totalDailyDuration = UserSession.getTotalDailyDuration()
    val minimumDailyGoal = UserSession.minimumGoal.collectAsState()
    val maximumDailyGoal = UserSession.maximumGoal.collectAsState()

    var goalToDisplay =
        if (totalDailyDuration.hours < minimumDailyGoal.value.hours &&
            totalDailyDuration.minutes < minimumDailyGoal.value.minutes
        ) {
            "${minimumDailyGoal.value.hours}h ${minimumDailyGoal.value.minutes}m"
        } else {
            "${maximumDailyGoal.value.hours}h ${maximumDailyGoal.value.minutes}m"
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
            "${totalDailyDuration.hours}h ${totalDailyDuration.minutes}m / $goalToDisplay",
            style = MaterialTheme.typography.titleLarge
        )

    }
}

