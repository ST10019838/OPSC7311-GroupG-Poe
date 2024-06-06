package com.example.chronometron.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import com.example.chronometron.types.TimeEntry

@Composable
fun TimeEntryDeletionDialog(onDismiss: () -> Unit, onConfirm: () -> Unit){
    AlertDialog(
        icon = {
            Icon(Icons.Default.Delete, contentDescription = "Delete Entry")
        },
        title = {
            Text(text = "Delete Entry")
        },
        text = { Text("Are you sure you want to delete this entry? ") },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Dismiss")
            }
        }
    )
}