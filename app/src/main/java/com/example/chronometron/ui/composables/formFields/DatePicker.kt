package com.example.chronometron.ui.composables.formFields

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ch.benlu.composeform.formatters.dateShort
import com.example.chronometron.utils.buildFieldLabel
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    value: Date?,
    label: String,
    onConfirm: (Date?) -> Unit = {},
    isRequired: Boolean = false,
    color: TextFieldColors? = null,
    placeholderText: String? = null,
    hasError: Boolean = false,
    errorText: MutableList<String> = mutableListOf(),
    resetOnClose: Boolean = false,

    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    isReadOnly: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isPickerOpen by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = buildFieldLabel(label = label, isRequired = isRequired),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 10.dp)
        )


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        isPickerOpen = true
                        focusManager.clearFocus()
                    }
                },
            singleLine = true,
            value = dateShort(value),
            onValueChange = {},
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
            keyboardActions = keyBoardActions,
            enabled = isEnabled,
            colors = color ?: OutlinedTextFieldDefaults.colors(),
            isError = hasError,
            readOnly = isReadOnly,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() },
            visualTransformation = visualTransformation,
            placeholder = {
                Text(
                    text = placeholderText ?: "",
                    maxLines = 1,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                )
            },
        )

        if (hasError) {
            Text(
                text = errorText.joinToString("\n"),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
            )
        }
    }


    if (isPickerOpen) {
        val datePickerState =
            rememberDatePickerState(if (!resetOnClose) value?.time?.milliseconds?.inWholeMilliseconds else null)
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                isPickerOpen = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(Date(datePickerState.selectedDateMillis!!))
                        isPickerOpen = false
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isPickerOpen = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}