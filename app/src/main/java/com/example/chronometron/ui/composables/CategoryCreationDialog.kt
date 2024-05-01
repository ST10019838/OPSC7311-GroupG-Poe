package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.chronometron.types.Category
import com.example.chronometron.ui.viewModels.UserSession
import java.util.UUID


@Composable
fun CategoryCreationDialog(onDismiss: () -> Unit = {}) {
    var text by remember { mutableStateOf<String>("") }

    AlertDialog(
        icon = {
            Icon(Icons.Default.Category, contentDescription = "Categories")
        },
        title = {
            Text(text = "Create Category")
        },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Category Name") }
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    UserSession.addCategory(Category(id = UUID.randomUUID(), name = text))
                    onDismiss()
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