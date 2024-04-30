package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chronometron.types.Period
import com.example.chronometron.ui.composables.formFields.DatePicker
import java.util.Date

@Composable
fun SelectablePeriodSearch(
    onSelectionChange: (Period?) -> Unit = { },
) {
    var fromDate: Date? by remember { mutableStateOf(null) }
    var toDate: Date? by remember { mutableStateOf(null) }


    CollapsibleSection(
        heading = "Search",
        icon = { Icon(Icons.Default.Search, contentDescription = "Search") }) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker(
                label = "From",
                value = fromDate,
                onConfirm = {
                    fromDate = it
                    onSelectionChange(Period(fromDate, toDate))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                trailingIcon = {
                    if (fromDate != null) {
                        IconButton(onClick = {
                            fromDate = null
                            onSelectionChange(Period(fromDate, toDate))
                        }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove From Date")
                        }
                    }

                }
            )

//            HorizontalDivider(
//                thickness = 2.dp, modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.1f)
//            )

            DatePicker(
                label = "To",
                value = toDate,
                onConfirm = {
                    toDate = it
                    onSelectionChange(Period(fromDate, toDate))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                trailingIcon = {
                    if (toDate != null) {
                        IconButton(onClick = {
                            toDate = null
                            onSelectionChange(Period(fromDate, toDate))
                        }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove To Date")
                        }
                    }
                }

            )
        }
    }
}