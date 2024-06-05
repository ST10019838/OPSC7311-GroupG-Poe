package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.chronometron.forms.CategoryCreationForm
import com.example.chronometron.types.Category
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.onFormValueChange
import java.util.UUID


@Composable
fun CategoryCreationDialog(onDismiss: () -> Unit = {}) {
    val form = CategoryCreationForm()

    AlertDialog(
        icon = {
            Icon(
                Icons.Default.Category,
                contentDescription = "Categories",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(text = "Create Category")
        },
        text = {
            TextField(
                value = form.name.state.value!!,
                label = "Name",
                isRequired = true,
                onChange = {
                    onFormValueChange(
                        value = it,
                        form = form,
                        fieldState = form.name
                    )
                },
                hasError = form.name.hasError(),
                errorText = form.name.errorText,
                placeholderText = "Add a Name"
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    form.validate(true)
                    if (form.isValid) {
                        UserSession.addCategory(
                            Category(
                                id = UUID.randomUUID(),
                                name = form.name.state.value!!
                            )
                        )
                        onDismiss()
                    }
                },

                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}