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
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.ui.viewModels.SessionState

@Composable
fun CategoryHoursScreen() {
    val categoryHours by SessionState.categoryHours.collectAsState()
    val timeEntries by SessionState.timeEntries.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (timeEntries.isEmpty()) {
            Text(
                "No Time Entries Created",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else if (categoryHours.isEmpty()) {
            Text(
                "No time has been recorded during this period",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
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
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {

            items(categoryHours.toList()) {
                CategoryHoursListItem(it)
            }
        }
    }
}

@Composable
private fun CategoryHoursListItem(item: Pair<Category, FirebaseHours>) {
    OutlinedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                item.first.name!!,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                "${item.second.hours}h ${item.second.minutes}m",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}

