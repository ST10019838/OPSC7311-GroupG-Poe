package com.example.chronometron.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ch.benlu.composeform.fields.DateField
import ch.benlu.composeform.formatters.dateShort
import com.example.chronometron.ui.composables.formFields.DatePicker

@Composable
fun SearchBar(){
    CollapsibleSection(heading = "Search") {
        Row {
            DatePicker(
                label = "From",
                form = form,
                fieldState = form.date,
                formatter = ::dateShort
            )

            DatePicker(
                label = "To",
                form = form,
                fieldState = form.date,
                formatter = ::dateShort
            )
        }
    }
}