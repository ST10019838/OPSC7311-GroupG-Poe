package com.example.chronometron.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalInput(state: Int, changeState: (Int) -> Unit) {
    Column {
//        OutlinedTextField(
//            value = "${state}",
//            onValueChange = {state = it.toInt()},
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )
//        OutlinedTextField(
//            value = "${state}",
//            onValueChange = {changeState(it.toInt())},
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//        )


        var minimumGoal by remember { mutableStateOf<Hours>(FullHours(12, 43)) }
        var maximumGoal by remember { mutableStateOf<Hours>(FullHours(12, 43)) }

        Column {

        }
        HoursNumberPicker(
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
            dividersColor = MaterialTheme.colorScheme.primary,
            leadingZero = true,
            value = minimumGoal,
            onValueChange = {
                minimumGoal = it
            },
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
            },
        )



        HoursNumberPicker(
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
            dividersColor = MaterialTheme.colorScheme.primary,
            leadingZero = true,
            value = maximumGoal,
            onValueChange = {
                maximumGoal = it
            },
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
            },
//            modifier = Modifier.padding(25.dp)
        )

//        NumberPicker(
//            value = pickerValue,
//            range = 0..24,
//            onValueChange = {
//                pickerValue = it
//            },
//            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
//            dividersColor = MaterialTheme.colorScheme.primary,
//        )
    }
}