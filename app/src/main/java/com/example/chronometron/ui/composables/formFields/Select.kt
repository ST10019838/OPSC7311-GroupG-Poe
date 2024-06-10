package com.example.chronometron.ui.composables.formFields


//import androidx.compose.material3.MenuAnchorType
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.chronometron.utils.buildFieldLabel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Select(
    modifier: Modifier = Modifier,
    value: T?,
    label: String,
    onSelect: (T) -> Unit = {},
    isRequired: Boolean = false,
    color: TextFieldColors? = null,
    placeholderText: String? = null,
    hasError: Boolean = false,
    errorText: MutableList<String> = mutableListOf(),
    options: List<T> = emptyList(),
    itemFormatter: ((T?) -> String)? = null,

    leadingIcon: @Composable() (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    focusChanged: ((focus: FocusState) -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester(),
    visualTransformation: VisualTransformation = VisualTransformation.None,

    canCreateIfEmpty: Boolean = false,
    onCreate: () -> Unit = {},
    creationContent: @Composable () -> Unit = {}
) {

    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = buildFieldLabel(label = label, isRequired = isRequired),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 10.dp),
                color = MaterialTheme.colorScheme.onSurface
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        focusChanged?.invoke(it)
                    }
                    .menuAnchor(),
                value = if (value == null) "" else itemFormatter?.invoke(value) ?: value.toString(),
                onValueChange = {},
                leadingIcon = leadingIcon,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction,
                    keyboardType = keyboardType
                ),
                keyboardActions = keyBoardActions,
                enabled = isEnabled,
                //colors = ExposedDropdownMenuDefaults.textFieldColors(),
                colors = color ?: OutlinedTextFieldDefaults.colors(),
                isError = hasError,
                readOnly = true,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                visualTransformation = visualTransformation,
                placeholder = {
                    Text(text = placeholderText ?: "")
                },

                )

            if (hasError) {
                Text(
                    text = errorText.joinToString("\n"),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = TextStyle.Default.copy(color = MaterialTheme.colorScheme.error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }


        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.heightIn(0.dp, 200.dp).fillMaxWidth().padding(horizontal = 10.dp)
        ) {
            if(options.isEmpty() && canCreateIfEmpty){
                onCreate()
                creationContent()
            }

            options.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (value == option),
                            onClick = {
                                onSelect(option)
                                isExpanded = false
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == value),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = itemFormatter?.invoke(option) ?: option.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
