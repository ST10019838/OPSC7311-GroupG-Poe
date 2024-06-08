package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.chronometron.forms.EntryCreationForm
import com.example.chronometron.types.Category
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.composables.CategoryCreationDialog
import com.example.chronometron.ui.composables.formFields.DatePicker
import com.example.chronometron.ui.composables.formFields.ImageCapturer
import com.example.chronometron.ui.composables.formFields.Select
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.ui.composables.formFields.TimeSelector
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.onFormValueChange
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeEntryManagementScreen(
    navigationAction: () -> Unit = {},
    entryToManage: TimeEntry? = null
) {
    // Initialize form with the entry to manage or create a new one
    var form = EntryCreationForm(entryToManage)
    val noEntryToManage = entryToManage == null
    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    // Function to handle the creation of a new entry
    val onCreate = {
        form.validate(true)
        if (form.isValid) {
            UserSession.addTimeEntry(form.produceEntry())
            navigationAction()
        }
    }

    // Function to handle the update of an existing entry
    val onUpdate = {
        form.validate(true)
        if (form.isValid) {
            // Ensure the ID of the entry to manage is used for the update
            val entry = form.produceEntry().copy(id = entryToManage?.id ?: UUID.randomUUID().toString())
            UserSession.updateTimeEntry(entry)
            navigationAction()
        }
    }

    // Function to handle the deletion of an existing entry
    val onDelete = {
        UserSession.deleteTimeEntry(entryToManage!!)
        navigationAction()
    }

    val openDeleteDialog = {
        isDeleteDialogOpen = true
    }

    // Fullscreen dialog for entry creation and management
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { }) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text("Entry Creation", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }, navigationIcon = {
                IconButton(onClick = navigationAction) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow Back")
                }
            }, actions = {
                TextButton(onClick = if (noEntryToManage) onCreate else onUpdate) {
                    Text(if (noEntryToManage) "Create" else "Update")
                }
            })
        }) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 25.dp)
                    .verticalScroll(rememberScrollState())  // Enable vertical scrolling
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    // Description field
                    TextField(
                        value = form.description.state.value,
                        label = "Description",
                        isRequired = true,
                        onChange = {
                            onFormValueChange(value = it, form = form, fieldState = form.description)
                        },
                        hasError = form.description.hasError(),
                        errorText = form.description.errorText,
                        placeholderText = "Add a Description"
                    )

                    // Date picker field
                    DatePicker(
                        label = "Date",
                        value = form.date.state.value,
                        isRequired = true,
                        onConfirm = {
                            onFormValueChange(value = it, form = form, fieldState = form.date)
                        },
                        hasError = form.date.hasError(),
                        errorText = form.date.errorText,
                        placeholderText = "Select a Date"
                    )

                    // Start time selector
                    TimeSelector(
                        label = "Start Time",
                        value = form.startTime.state.value,
                        useSemicolonDivider = true,
                        isRequired = true,
                        onChange = {
                            onFormValueChange(value = it, form = form, fieldState = form.startTime)
                        },
                        hasError = form.startTime.hasError(),
                        errorText = form.startTime.errorText,
                    )

                    // End time selector
                    TimeSelector(
                        label = "End Time",
                        value = form.endTime.state.value,
                        useSemicolonDivider = true,
                        isRequired = true,
                        onChange = {
                            onFormValueChange(value = it, form = form, fieldState = form.endTime)
                        },
                        hasError = form.endTime.hasError(),
                        errorText = form.endTime.errorText,
                    )

                    // Category selection
                    Select<Category?>(
                        label = "Category",
                        value = form.category.state.value,
                        options = UserSession.categories.collectAsState().value,
                        itemFormatter = form.category.optionItemFormatter,
                        isRequired = true,
                        onSelect = {
                            onFormValueChange(value = it, form = form, fieldState = form.category)
                        },
                        hasError = form.category.hasError(),
                        errorText = form.category.errorText,
                        placeholderText = "Select a Category",
                        canCreateIfEmpty = true,
                        creationContent = {
                            var isDialogOpen by rememberSaveable { mutableStateOf(false) }

                            Button(onClick = { isDialogOpen = true }, modifier = Modifier.fillMaxWidth()) {
                                Text("Create Category")
                            }

                            if (isDialogOpen) {
                                CategoryCreationDialog(onDismiss = { isDialogOpen = false })
                            }
                        }
                    )

                    // Photograph capture field
                    ImageCapturer(
                        label = "Photograph",
                        value = form.photograph.state.value,
                        onChange = {
                            onFormValueChange(value = it, form = form, fieldState = form.photograph)
                        },
                        hasError = form.photograph.hasError(),
                        errorText = form.photograph.errorText
                    )

                    // Button for creating or deleting an entry
                    Button(
                        onClick = if (noEntryToManage) onCreate else openDeleteDialog,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (noEntryToManage) Color.Unspecified else MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(if (noEntryToManage) "Create Entry" else "Delete Entry")
                    }

                    // Delete confirmation dialog
                    if (isDeleteDialogOpen) {
                        AlertDialog(
                            icon = {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Entry")
                            },
                            title = {
                                Text(text = "Delete Entry")
                            },
                            text = { Text("Are you sure you want to delete this entry?") },
                            onDismissRequest = {
                                isDeleteDialogOpen = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    onDelete()
                                    isDeleteDialogOpen = false
                                }) {
                                    Text("Confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    isDeleteDialogOpen = false
                                }) {
                                    Text("Dismiss")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
