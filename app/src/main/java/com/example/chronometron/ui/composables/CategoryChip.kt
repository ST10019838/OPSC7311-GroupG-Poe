package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle


@Composable
fun CategoryChip(
    category: String,
    modifier: Modifier = Modifier,
    confirmationAction: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    InputChip(
        onClick = {
            openDialog = true
        },
        label = {
            Text(
                category,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
//        avatar = {
//            Icon(
//                Icons.Filled.Person,
//                contentDescription = "Localized description",
//                Modifier.size(InputChipDefaults.AvatarSize)
//            )
//        },
        trailingIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Localized description",
                Modifier.size(InputChipDefaults.AvatarSize)
            )
        },
        modifier = modifier
    )

    if (openDialog) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove Category",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text(text = "Remove Category")
            },
            text = {
                Row {
                    Text(buildAnnotatedString {
                        append("Are you sure you want to remove the category: ")

                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${category}")
                        }

                    })
                }
            },
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmationAction()
                        openDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}
