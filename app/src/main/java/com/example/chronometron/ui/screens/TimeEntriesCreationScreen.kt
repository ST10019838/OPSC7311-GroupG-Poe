package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ch.benlu.composeform.fields.PickerField
import com.example.chronometron.forms.EntryCreationForm
import com.example.chronometron.types.Category
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
fun TimeEntriesCreationScreen(navigationAction: () -> Unit = {}, onCreate: () -> Unit = {}) {
    //1.  list of entries
//    var entries by remember(mutableStateOf<List<TimeEntries>>(getTimeEntries()))

//   // 1.1. loop through entries in a list
//      value.foreach {
//          TimesheetListEntry(it)
//      }
//
//    // creation button & daily goal display

    var openDropdown by remember { mutableStateOf(false) }
    var form = EntryCreationForm()

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
                TextButton(onClick = {
                    form.validate(true)
                    if (form.isValid) {
                        UserSession.addTimeEntry(form)
                        navigationAction()
                    }
                }) {
                    Text("Create")
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
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
//                    TextField(
//                        label = "Description*",
//                        form = form,
//                        fieldState = form.description,
//                    ).Field()

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


//                    TextField(
//                        label = "Duration",
//                        form = form,
//                        fieldState = form.duration
//                    ).Field()

//                    TimeSelector(
//                        label = "Duration",
//                        form = form,
//                        fieldState = form.duration,
//                        useSemicolonDivider = true,
//                    ).Field()

//                    DateField(
//                        label = "Date*",
//                        form = form, fieldState = form.date, formatter = ::dateShort
//                    ).Field()

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
                                value = it,
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
                                value = it,
                                form = form,
                                fieldState = form.endTime
                            )
                        },
                        hasError = form.endTime.hasError(),
                        errorText = form.endTime.errorText,

                        )

//                    TimeSelector(
//                        label = "Duration:",
//                        form = form,
//                        fieldState = form.duration,
//                        useSemicolonDivider = true
//                    ).Field()
//

//                    PickerField(
//                        label = "Category*", form = form, fieldState = form.category
//                    ).Field()


                    Select<Category?>(
                        label = "Category",
                        value = form.category.state.value,
                        options = form.category.options.toList(),
                        itemFormatter = form.category.optionItemFormatter,

                        //                        value = form.category.state.value,
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
                        placeholderText = "Select a Category"
                    )



                    ImageCapturer(
                        label = "Photograph",
                        value = form.photograph.state.value,
                        isRequired = true,
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
                }


            }
        }
    }
}



