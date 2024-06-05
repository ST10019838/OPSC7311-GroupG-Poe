package com.example.chronometron.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.utils.hoursToFloat

@Composable
fun DailyHoursGraph(
    graphData: List<Point>,
    minimumGoal: Hours? = null,
    maximumGoal: Hours? = null
) {
    val steps = 4

    // This line is required as it allows for proper mapping of point data
    val highestLine: List<Point> = listOf(
        Point(0f, 24f),
        Point(graphData.last().x, 24f),
    )

    val minHoursGoalData: List<Point> = if (minimumGoal == null) {
        listOf(Point(0f, 0f))
    } else {
        listOf(
            Point(0f, hoursToFloat(minimumGoal)),
            Point(graphData.last().x, hoursToFloat(minimumGoal)),
        )
    }


    val maxHoursGoalData: List<Point> = if (maximumGoal == null) {
        listOf(Point(0f, 0f))
    } else {
        listOf(
            Point(0f, hoursToFloat(maximumGoal)),
            Point(graphData.last().x, hoursToFloat(maximumGoal)),
        )
    }


    // The following code was adapted from Youtube
    // Author: Stevdza-San
    // Link: https://www.youtube.com/watch?v=HGsVBqUrnGY
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .axisLineColor(MaterialTheme.colorScheme.primary)
        .axisLabelColor(MaterialTheme.colorScheme.onSurface)
        .steps(graphData.size - 1)
        .labelData { i -> graphData[i].description }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
//            .axisOffset(200.dp)
//            .startDrawPadding(50.dp)
        .shouldDrawAxisLineTillEnd(true)
        .axisLabelColor(MaterialTheme.colorScheme.onSurface)
        .axisLineColor(MaterialTheme.colorScheme.primary)
        .steps(steps)
        .backgroundColor(MaterialTheme.colorScheme.surface)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 24 / steps
            (i * yScale).toString()
        }
        .build()


    val lineChartData = LineChartData(
        containerPaddingEnd = 100.dp,
//            paddingTop = 200.dp,
//            bottomPadding = 200.dp,
//            paddingRight = 0.dp,
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = highestLine, LineStyle(
                        color = Color.Transparent,
                        lineType = LineType.Straight()
                    )
                ),
                Line(
                    dataPoints = minHoursGoalData,
                    LineStyle(
                        // Make color green
                        color = Color.Green,
                        lineType = LineType.SmoothCurve()
                    )
                ),
                Line(
                    dataPoints = maxHoursGoalData,
                    LineStyle(
                        color = Color.Red,
                        lineType = LineType.SmoothCurve()
                    )
                ),
                Line(
                    dataPoints = graphData, LineStyle(
                        color = MaterialTheme.colorScheme.primary,
                        lineType = LineType.Straight()
                    ), IntersectionPoint(
                        color = MaterialTheme.colorScheme.primary,
                        alpha = 0f
                    ), SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.primary,
//                            alpha = 0f
                    ), ShadowUnderLine(
                        alpha = 0.5f, brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary, Color.Transparent
                            )
                        )
                    ), SelectionHighlightPopUp(popUpLabel = { x, y ->
                        "${graphData[x.toInt()].y} hours"
                    })
                ),


//
                // Add max and min goal lines
//                    Line(dataPoints = minHoursGoalData,
//                        LineStyle(
//                            // Make color green
//                            color = MaterialTheme.colorScheme.primary,
//                            lineType = LineType.SmoothCurve()
//                        ),
//                        IntersectionPoint(
//                            radius = 0.dp
//                        ),
//                        SelectionHighlightPoint(radius = 0.dp, alpha = 0f),
//                        ShadowUnderLine(
//                            alpha = 0.5f,
//                            brush = Brush.verticalGradient(
//                                colors = listOf(
//                                    MaterialTheme.colorScheme.primary,
//                                    Color.Transparent
//                                )
//                            )
//                        ),
//                        SelectionHighlightPopUp(
//                            popUpLabel = { x, y ->
//                                "${y * 24} hours"
//                            }
//                        )),
//                    Line(
//                        dataPoints = maxHoursGoalData,
//                        LineStyle(
//                            color = MaterialTheme.colorScheme.error,
//                            lineType = LineType.SmoothCurve()
//                        ),
//                        IntersectionPoint(
//                            radius = 0.dp
//                        ),
//                        SelectionHighlightPoint(radius = 0.dp, alpha = 0f),
//                        ShadowUnderLine(),
//                        SelectionHighlightPopUp()
//                    )

            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            color = MaterialTheme.colorScheme.outlineVariant,
            enableHorizontalLines = true,
            enableVerticalLines = true,
        ), backgroundColor = MaterialTheme.colorScheme.surface
    )


    var openKey by remember { mutableStateOf(false) }

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Daily Hours Graph",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                Icons.Default.Key,
                contentDescription = "Key",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { openKey = true }
            )
        }

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp),
            lineChartData = lineChartData,
        )
    }

    if (openKey) {
        GraphKey(onDismiss = {
            openKey = false
        })
    }
}


@Composable
private fun GraphKey(onDismiss: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Default.Key,
                contentDescription = "Graph Key",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(text = "Graph Key")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Divider(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp),
                        color = Color.Green
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Minimum Goal")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Divider(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Daily Hours Recorded")
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Divider(
                        modifier = Modifier
                            .width(20.dp)
                            .height(2.dp),
                        color = Color.Red
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Maximum Goal")
                }

                Text("X-Axis : Days Recorded")
                Text("Y-Axis : Hours per Day")
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

