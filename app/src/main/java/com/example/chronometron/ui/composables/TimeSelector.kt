package com.example.chronometron.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.Field
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker

@OptIn(ExperimentalFoundationApi::class)
class TimeSelector(
    private val useSemicolonDivider: Boolean = false,
    label: String,
    form: Form,
    modifier: Modifier? = Modifier,
    fieldState: FieldState<Hours?>,
    isEnabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    formatter: ((raw: String?) -> String)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    changed: ((v: Hours?) -> Unit)? = null,


    action: () -> Unit = {},
    actionIcon: @Composable () -> Unit = {}
) : Field<Hours>(
    label = label,
    form = form,
    fieldState = fieldState,
    isEnabled = isEnabled,
    modifier = modifier,
    imeAction = imeAction,
    keyboardType = keyboardType,
    visualTransformation = visualTransformation,
    changed = changed,
) {

    @SuppressLint("NotConstructor")
    @Composable
    override fun Field() {
        this.updateComposableValue()
        if (!fieldState.isVisible()) {
            return
        }


        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label)

//                FilledIconButton(onClick = action) {
//                    actionIcon()
//                }
            }


            HoursNumberPicker(
                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary),
                dividersColor = MaterialTheme.colorScheme.primary,
                leadingZero = true,
                value = fieldState.state.value!!,
                onValueChange = {
                    onChange(it, form)
                },
                hoursDivider = {
                    if(useSemicolonDivider){
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
                    if(!useSemicolonDivider){
                        Text(
                            textAlign = TextAlign.Center,
                            text = "minutes",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            )

            if (fieldState.hasError()) {
                Text(
                    text = fieldState.errorText.joinToString("\n"),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
                )
            }

        }
    }
}