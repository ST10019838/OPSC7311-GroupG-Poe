package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.chronometron.forms.CategoryCreationForm
import com.example.chronometron.types.Category
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.onFormValueChange

/**
 * Composable function to display a dialog for creating a new category.
 * This dialog allows users to input a category name and validate it.
 *
 * @param onDismiss Lambda function to handle the dialog dismissal.
 */
@Composable
fun CategoryCreationDialog(onDismiss: () -> Unit = {}) {
    // Create an instance of the CategoryCreationForm.
    val form = CategoryCreationForm()

    // Display the AlertDialog.
    AlertDialog(
        // Icon displayed in the dialog.
        icon = {
            Icon(Icons.Default.Category, contentDescription = "Categories")
        },
        // Title of the dialog.
        title = {
            Text(text = "Create Category")
        },
        // Text field for entering the category name.
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
        // Handler for dismissing the dialog.
        onDismissRequest = onDismiss,
        // Button to confirm the creation of the category.
        confirmButton = {
            Button(
                onClick = {
                    // Validate the form and add the category if valid.
                    form.validate(true)
                    if (form.isValid) {
                        val category = Category(name = form.name.state.value!!)
                        UserSession.addCategory(category)
                        onDismiss()
                    }
                },
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                Text("Create")
            }
        },
        // Button to cancel the dialog.
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
