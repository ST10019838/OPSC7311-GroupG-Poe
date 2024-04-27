package com.example.chronometron.ui.screens

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import ch.benlu.composeform.fields.DateField
import ch.benlu.composeform.fields.PickerField
import ch.benlu.composeform.fields.TextField
import ch.benlu.composeform.formatters.dateShort
import com.example.chronometron.ui.composables.TimeSelector
import com.example.chronometron.ui.forms.EntryCreationForm
import java.util.Date
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeEntriesCreationScreen(navigationAction: () -> Unit = {}, action: () -> Unit = {}) {
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
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Entry Creation",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = navigationAction) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Arrow Back"
                            )
                        }
                    },
                    actions = {
                        TextButton(onClick = { form.validate(true) }) {
                            Text("Create")
                        }
                    })
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 25.dp)
            ) {
//                Column {
//                    var description by rememberSaveable { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = description,
//                        onValueChange = { description = it },
//                        label = { Text("Label") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    var duration by rememberSaveable { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = duration,
//                        onValueChange = { duration = it },
//                        label = { Text("Label") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    var Date by rememberSaveable { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = Date,
//                        onValueChange = { Date = it },
//                        label = { Text("Label") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    var Category by rememberSaveable { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = Category,
//                        onValueChange = { Category = it },
//                        label = { Text("Label") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    var photo by rememberSaveable { mutableStateOf("") }
//                    OutlinedTextField(
//                        value = photo,
//                        onValueChange = { photo = it },
//                        label = { Text("Label") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    TextField(
                        label = "Description",
                        form = form,
                        fieldState = form.description
                    ).Field()
//
//                    TextField(
//                        label = "Duration",
//                        form = form,
//                        fieldState = form.duration
//                    ).Field()


//
                    DateField(
                        label = "Date",
                        form = form,
                        fieldState = form.date,
                        formatter = ::dateShort
                    ).Field()

                    TimeSelector(
                        label = "Start Time:",
                        form = form,
                        fieldState = form.startTime,
                        useSemicolonDivider = true
                    ).Field()

                    TimeSelector(
                        label = "End Time:",
                        form = form,
                        fieldState = form.endTime,
                        useSemicolonDivider = true
                    ).Field()

//                    TimeSelector(
//                        label = "Duration:",
//                        form = form,
//                        fieldState = form.duration,
//                        useSemicolonDivider = true
//                    ).Field()
//
//                    TextField(
//                        label = "End Time",
//                        form = form,
//                        fieldState = form.endTime
//                    ).Field()
//

                    PickerField(
                        label = "Category",
                        form = form,
                        fieldState = form.category
                    ).Field()
//
//                    TextField(
//                        label = "Photograph",
//                        form = form,
//                        fieldState = form.photograph
//                    ).Field()
                }


            }
        }
    }
}



