package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.chronometron.api.addTimeEntry
import com.example.chronometron.api.deleteTimeEntry
import com.example.chronometron.api.updateTimeEntry
import com.example.chronometron.forms.EntryCreationForm
import com.example.chronometron.types.Category
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.ui.composables.CategoryCreationDialog
import com.example.chronometron.ui.composables.TimeEntryDeletionDialog
import com.example.chronometron.ui.composables.formFields.DatePicker
import com.example.chronometron.ui.composables.formFields.ImageCapturer
import com.example.chronometron.ui.composables.formFields.Select
import com.example.chronometron.ui.composables.formFields.TextField
import com.example.chronometron.ui.composables.formFields.TimeSelector
import com.example.chronometron.ui.viewModels.SessionState
import com.example.chronometron.utils.onFormValueChange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeEntryManagementScreen(
    navigationAction: () -> Unit = {},
    entryToManage: TimeEntry? = null
) {
    var form = EntryCreationForm(entryToManage)
    val noEntryToManage = entryToManage == null
    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    val onCreate = {
        form.validate(true)
        if (form.isValid) {
            addTimeEntry(form.produceEntry())
            navigationAction()
        }
    }

    val onUpdate = {
        form.validate(true)
        if (form.isValid) {
            updateTimeEntry(form.produceEntry(isAlreadyArchived = entryToManage?.isArchived ?: false))
            navigationAction()
        }
    }

    val onDelete = {
        deleteTimeEntry(entryToManage!!)
        navigationAction()
    }

    val openDeleteDialog = {
        isDeleteDialogOpen = true
    }

    // The following dialog was taken from stackoverflow.com
    // Author: jns
    // Link: https://stackoverflow.com/questions/65243956/jetpack-compose-fullscreen-dialog
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { }) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    "Entry Creation", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = navigationAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }, actions = {
//                TextButton(onClick = onCreate) {
//                    Text("Create")
//                }

                TextButton(
                    onClick = if (noEntryToManage) onCreate else onUpdate,
                ) {
                    Text(if (noEntryToManage) "Create" else "Update")
                }
            })
        }) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 20.dp)
                ) {
                    TextField(
                        value = form.description.state.value,
                        label = "Description",
                        isRequired = true,
                        onChange = {
                            onFormValueChange(
                                value = it,
                                form = form,
                                fieldState = form.description
                            )
                        },
                        hasError = form.description.hasError(),
                        errorText = form.description.errorText,
                        placeholderText = "Add a Description"
                    )

                    DatePicker(
                        label = "Date",
                        value = form.date.state.value,
                        isRequired = true,
                        onConfirm = {
                            onFormValueChange(
                                value = it,
                                form = form,
                                fieldState = form.date
                            )
                        },
                        hasError = form.date.hasError(),
                        errorText = form.date.errorText,
                        placeholderText = "Select a Date"
                    )


                    TimeSelector(
                        label = "Start Time",
                        value = form.startTime.state.value,
                        useSemicolonDivider = true,
                        isRequired = true,
                        onChange = {
                            onFormValueChange(
                                value = FirebaseHours(it.hours, it.minutes),
                                form = form,
                                fieldState = form.startTime
                            )
                        },
                        hasError = form.startTime.hasError(),
                        errorText = form.startTime.errorText,
                    )

                    TimeSelector(
                        label = "End Time",
                        value = form.endTime.state.value,
                        useSemicolonDivider = true,
                        isRequired = true,
                        onChange = {
                            onFormValueChange(
                                value = FirebaseHours(it.hours, it.minutes),
                                form = form,
                                fieldState = form.endTime
                            )
                        },
                        hasError = form.endTime.hasError(),
                        errorText = form.endTime.errorText,

                        )

                    Select<Category?>(
                        label = "Category",
                        value = form.category.state.value,
                        options = SessionState.categories.collectAsState().value,
                        itemFormatter = form.category.optionItemFormatter,
                        isRequired = true,
                        onSelect = {
                            onFormValueChange(
                                value = it,
                                form = form,
                                fieldState = form.category
                            )
                        },
                        hasError = form.category.hasError(),
                        errorText = form.category.errorText,
                        placeholderText = "Select a Category",
                        canCreateIfEmpty = true,
                        creationContent = {
                            var isDialogOpen by rememberSaveable { mutableStateOf(false) }

                            Button(
                                onClick = { isDialogOpen = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Create Category")
                            }

                            if (isDialogOpen) {
                                CategoryCreationDialog(onDismiss = {
                                    isDialogOpen = false
                                })
                            }
                        }
                    )



                    ImageCapturer(
                        label = "Photograph",
                        value = form.photograph.state.value,
                        onChange = {
                            onFormValueChange(
                                value = it,
                                form = form,
                                fieldState = form.photograph
                            )
                        },
                        hasError = form.photograph.hasError(),
                        errorText = form.photograph.errorText
                    )

                    Button(
                        onClick = if (noEntryToManage) onCreate else openDeleteDialog,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (noEntryToManage) Color.Unspecified
                            else MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(if (noEntryToManage) "Create Entry" else "Delete Entry")
                    }


                    if (isDeleteDialogOpen) {
                        TimeEntryDeletionDialog(
                            onDismiss = { isDeleteDialogOpen = false },
                            onConfirm = onDelete
                        )
                    }

                }
            }
        }
    }
}



