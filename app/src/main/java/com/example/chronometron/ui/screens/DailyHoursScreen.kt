package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chronometron.ui.composables.DailyHoursGraph
import com.example.chronometron.ui.composables.GoalAdherenceRating
import com.example.chronometron.ui.viewModels.SessionState
import com.example.chronometron.utils.hoursToFloat

@Composable
fun DailyHoursScreen(){
    val timeEntries by SessionState.timeEntries.collectAsStateWithLifecycle()
    val graphData by SessionState.graphData.collectAsStateWithLifecycle()
    val minimumGoal by SessionState.minimumGoal.collectAsStateWithLifecycle()
    val maximumGoal by SessionState.maximumGoal.collectAsStateWithLifecycle()


    if (timeEntries.isEmpty()) {
        Text(
            "No Time Entries Created",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    } else if (graphData.isEmpty()) {
        Text(
            "No time has been recorded during this period",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(35.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 25.dp)
        ) {
            DailyHoursGraph(
                graphData,
                if (hoursToFloat(minimumGoal) == 0f) null else minimumGoal,
                if (hoursToFloat(maximumGoal) == 0f) null else maximumGoal
            )


            if (hoursToFloat(minimumGoal) == 0f && hoursToFloat(maximumGoal) == 0f) {
                Text(
                    "Set goals to begin tracking adherence",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                GoalAdherenceRating(minimumGoal, maximumGoal, graphData)
            }
        }
    }
}