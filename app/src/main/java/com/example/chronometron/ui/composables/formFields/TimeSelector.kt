package com.example.chronometron.ui.composables.formFields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.example.chronometron.utils.buildFieldLabel

@Composable
fun TimeSelector(
    useSemicolonDivider: Boolean = false,
    label: String,
    isRequired: Boolean = false,
    value: Hours?,
    onChange: (Hours) -> Unit = {},
    hasError: Boolean = false,
    errorText: MutableList<String> = mutableListOf(),
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = buildFieldLabel(label = label, isRequired = isRequired),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )

        OutlinedCard(
            border = BorderStroke(
                width = 1.dp,
                color = if (hasError) colorScheme.error else colorScheme.primary
            ),
        ) {
            HoursNumberPicker(
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                dividersColor = if (hasError) colorScheme.error else colorScheme.primary,
                leadingZero = true,
                value = value ?: FullHours(0, 0),
                onValueChange = onChange,
                hoursDivider = {
                    if (useSemicolonDivider) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = ":",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    } else {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "hours",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                },
                minutesDivider = {
                    if (!useSemicolonDivider) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "minutes",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                },
                modifier = Modifier.padding(horizontal = 15.dp),
            )
        }

        if (hasError) {
            Text(
                text = errorText.joinToString("\n"),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
            )
        }

    }
}
