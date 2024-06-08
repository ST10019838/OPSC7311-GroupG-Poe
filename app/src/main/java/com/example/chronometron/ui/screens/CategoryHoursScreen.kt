package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.ui.composables.SelectablePeriodSearch
import com.example.chronometron.ui.viewModels.UserSession

/**
 * Composable function to display the category hours screen.
 * This screen shows a list of categories and the total time spent on each category
 * within a selected period.
 */
@Composable
fun CategoryHoursScreen() {
    // Collecting state values from UserSession ViewModel
    val categoryHours by UserSession.categoryHours.collectAsState()
    val selectablePeriod by UserSession.selectedPeriod.collectAsState()
    val timeEntries by UserSession.timeEntries.collectAsState()

    // Main container for the screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Period selection component
        SelectablePeriodSearch(
            value = selectablePeriod,
            onSelectionChange = {
                UserSession.onSelectedPeriodChange(
                    it?.fromDate,
                    it?.toDate
                )
            },
            isOpen = false
        )

        // Conditional UI based on the availability of time entries and category hours
        if (timeEntries.isEmpty()) {
            // Display message if no time entries are created
            Text(
                "No Time Entries Created",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else if (categoryHours.isEmpty()) {
            // Display message if no time is recorded in the selected period
            Text(
                "No time has been recorded during this period",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            // Display headers for the categories and total time
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Category",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    "Total Time",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // List of category hours
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                // Displaying each category and its total time
                items(categoryHours.toList()) {
                    CategoryHoursListItem(it)
                }
            }
        }
    }
}

/**
 * Composable function to display a single item in the category hours list.
 * @param item A pair containing a Category and the total Hours spent on that category.
 */
@Composable
private fun CategoryHoursListItem(item: Pair<Category, Hours>) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Display category name with safe call and fallback to a default value
            Text(
                item.first.name ?: "Unknown Category",
                color = MaterialTheme.colorScheme.onSurface
            )

            // Display total time in hours and minutes
            Text(
                "${item.second.hours}h ${item.second.minutes}m",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
