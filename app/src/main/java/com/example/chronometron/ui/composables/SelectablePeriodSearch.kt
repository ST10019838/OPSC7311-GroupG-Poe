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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chronometron.types.Period
import com.example.chronometron.ui.composables.formFields.DatePicker

@Composable
fun SelectablePeriodSearch(
    value: Period,
    onSelectionChange: (Period?) -> Unit = { },
    isOpen: Boolean = true
) {

    CollapsibleSection(
        heading = "Search",
        icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        isOpen = isOpen
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker(
                label = "From",
                value = value.fromDate,
                onConfirm = {
                    onSelectionChange(Period(it, value.toDate))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                trailingIcon = {
                    if (value.fromDate != null) {
                        IconButton(onClick = {
                            onSelectionChange(Period(null, value.toDate))
                        }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove From Date")
                        }
                    }

                }
            )

            DatePicker(
                label = "To",
                value = value.toDate,
                onConfirm = {
                    onSelectionChange(Period(value.fromDate, it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                trailingIcon = {
                    if (value.toDate != null) {
                        IconButton(onClick = {
                            onSelectionChange(Period(value.fromDate, null))
                        }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove To Date")
                        }
                    }
                }

            )
        }
    }
}