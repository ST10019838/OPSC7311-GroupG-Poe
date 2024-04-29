package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
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
import com.example.chronometron.ui.camera.ImageCapturer
import com.example.chronometron.ui.composables.TimeSelector
import com.example.chronometron.ui.forms.EntryCreationForm
import com.example.chronometron.ui.viewModels.UserSession

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
                    TextField(
                        label = "Description*",
                        form = form,
                        fieldState = form.description,
                    ).Field()


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

                    DateField(
                        label = "Date*",
                        form = form, fieldState = form.date, formatter = ::dateShort
                    ).Field()

                    TimeSelector(
                        label = "Start Time*",
                        form = form,
                        fieldState = form.startTime,
                        useSemicolonDivider = true,
                    ).Field()

                    TimeSelector(
                        label = "End Time*",
                        form = form,
                        fieldState = form.endTime,
                        useSemicolonDivider = true,
                        modifier = Modifier.height(50.dp)
                    ).Field()

//                    TimeSelector(
//                        label = "Duration:",
//                        form = form,
//                        fieldState = form.duration,
//                        useSemicolonDivider = true
//                    ).Field()
//

                    PickerField(
                        label = "Category*", form = form, fieldState = form.category
                    ).Field()


                    ImageCapturer(
                        label = "",
                        form = form,
                        fieldState = form.photograph,
                    ).Field()
                }


            }
        }
    }
}



