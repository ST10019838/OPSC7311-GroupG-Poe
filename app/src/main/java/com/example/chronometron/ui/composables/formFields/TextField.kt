package com.example.chronometron.ui.composables.formFields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.chronometron.utils.buildFieldLabel


// The following code was adapted from GitHub
// Author: benjamin-luescher
// Link: https://github.com/benjamin-luescher/compose-form/blob/main/composeform/src/main/java/ch/benlu/composeform/components/TextFieldComponent.kt
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String?,
    label: String,
    onChange: (String) -> Unit = {},
    isRequired: Boolean = false,
    color: TextFieldColors? = null,
    placeholderText: String? = null,
    hasError: Boolean = false,
    errorText: MutableList<String> = mutableListOf(),

    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    isReadOnly: Boolean = false,
    focusChanged: ((focus: FocusState) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {


    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {

        Text(
            text = buildFieldLabel(label = label, isRequired = isRequired),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 10.dp)
        )


        OutlinedTextField (
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    focusChanged?.invoke(it)
                },
            value = value ?: "",
            onValueChange = {
                onChange(it)
            },
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
                Text(text = placeholderText ?: "", )
            }
        )

        if (hasError) {
            Text(
                text = errorText.joinToString("\n"),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error)
            )
        }
    }
}