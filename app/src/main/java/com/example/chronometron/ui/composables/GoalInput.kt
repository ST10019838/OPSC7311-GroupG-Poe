package com.example.chronometron.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalSetter(
    title: String,
    value: Hours,
    onValueChange: (Hours) -> Unit,
    action: () -> Unit = {},
    actionIcon: @Composable () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)

            FilledIconButton(onClick = action) {
                actionIcon()
            }
        }


        HoursNumberPicker(
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
            dividersColor = MaterialTheme.colorScheme.primary,
            leadingZero = true,
            value = value,
            onValueChange = onValueChange,
            hoursDivider = {
                Text(
                    textAlign = TextAlign.Center,
                    text = "hours",
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            },
            minutesDivider = {
                Text(
                    textAlign = TextAlign.Center,
                    text = "minutes",
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        )


    }

}