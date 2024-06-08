package com.example.chronometron.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.Point
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.types.Rating
import com.example.chronometron.utils.hoursToFloat

@Composable
fun GoalAdherenceRating(
    minimumGoal: FirebaseHours,
    maximumGoal: FirebaseHours,
    graphData: List<Point>
) {
    val rating = CalculateAdherenceRating(minimumGoal, maximumGoal, graphData)
    var openDetails by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Goal Adherence Rating",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                Icons.Outlined.Info,
                contentDescription = "Rating Info",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { openDetails = true }
            )
        }


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Default.SentimentVeryDissatisfied,
                contentDescription = "Terrible",
                tint = if (rating.score == 1) Color.Red else MaterialTheme.colorScheme.outlineVariant
            )
            Icon(
                Icons.Default.SentimentDissatisfied, contentDescription = "Bad",
                tint = if (rating.score == 2) Color.Red else MaterialTheme.colorScheme.outlineVariant
            )
            Icon(
                Icons.Default.SentimentNeutral, contentDescription = "Ok",
                tint = if (rating.score == 3) Color.LightGray else MaterialTheme.colorScheme.outlineVariant
            )
            Icon(
                Icons.Default.SentimentSatisfiedAlt, contentDescription = "Good",
                tint = if (rating.score == 4) Color.Green else MaterialTheme.colorScheme.outlineVariant
            )
            Icon(
                Icons.Default.SentimentVerySatisfied,
                contentDescription = "Great",
                tint = if (rating.score == 5) Color.Green else MaterialTheme.colorScheme.outlineVariant
            )
        }

        Text(
            rating.text,
            color = rating.color,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (openDetails) {
        RatingDetails(onDismiss = {
            openDetails = false
        })
    }
}

@Composable
private fun RatingDetails(onDismiss: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Outlined.Info,
                contentDescription = "Rating Details",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(text = "Rating Details")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 250.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("The rating calculation formula:")
                    Text(
                        "(mg <= x <= mxg) / t",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("The formula reads:")
                    Text(
                        "All entries over a selected period (x), between and inclusive of the " +
                                "minimum (mg) and maximum goal (mxg) divided by the total entries " +
                                "over a selected period (t).",
                        textAlign = TextAlign.Justify
                    )
                }


                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ratings and Percentages:")

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.SentimentVeryDissatisfied,
                            contentDescription = "Terrible"
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("0%-25% (Terrible!)")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.SentimentDissatisfied, contentDescription = "Bad",
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("26%-44% (Bad)")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.SentimentNeutral, contentDescription = "Ok",
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("45%-55% (Ok)")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.SentimentSatisfiedAlt, contentDescription = "Good"
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("56%-79% (Good)")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.SentimentVerySatisfied,
                            contentDescription = "Great"
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("80%-100% (Great!)")
                    }
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Ok")
            }
        },
    )
}


private fun CalculateAdherenceRating(
    minimumGoal: FirebaseHours,
    maximumGoal: FirebaseHours,
    graphData: List<Point>
): Rating {
    // the minus one is for the initial point starting at 0,0
    val totalNumberOfEntries = graphData.count() - 1
    var totalEntriesWithinGoals = 0

    // the first value of the list is dropped as it is the 0,0 point
    // that doesnt count for the rating
    graphData.drop(1).forEach {
        if (hoursToFloat(minimumGoal) <= it.y && it.y <= hoursToFloat(maximumGoal)) {
            totalEntriesWithinGoals += 1
        }
    }


    val score = totalEntriesWithinGoals.toFloat() / totalNumberOfEntries.toFloat()

    return when {
        // the following code was adapted from stack overflow
        // Author: Shahab Saalami
        // Link: https://stackoverflow.com/questions/65637680/kotlin-check-if-a-variable-is-between-two-numbers
        (score in 0f..0.25f) -> Rating(score = 1, text = "Terrible!", color = Color.Red)
        (score in 0.26f..0.44f) -> Rating(score = 2, text = "Bad", color = Color.Red)
        (score in 0.45f..0.55f) -> Rating(score = 3, text = "Ok", color = Color.LightGray)
        (score in 0.56f..0.79f) -> Rating(score = 4, text = "Good", color = Color.Green)
        else -> { // Note the block
            Rating(score = 5, text = "Great!", color = Color.Green)
        }
    }
}