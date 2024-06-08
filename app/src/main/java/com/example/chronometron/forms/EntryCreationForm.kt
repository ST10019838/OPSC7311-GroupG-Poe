package com.example.chronometron.forms

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.NotEmptyValidator
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.forms.validators.IsRequiredValidator
import com.example.chronometron.forms.validators.MaxLengthValidator
import com.example.chronometron.forms.validators.TimeValidator
import com.example.chronometron.types.Category
import com.example.chronometron.types.SerializableHours
import com.example.chronometron.types.TimeEntry
import java.util.Date
import java.util.UUID

/**
 * A form class for creating or editing a time entry.
 * @param entry The TimeEntry object to edit or null for a new entry.
 */
class EntryCreationForm(entry: TimeEntry?) : Form() {

    /**
     * Method to return the current form instance.
     */
    override fun self(): Form {
        return this
    }

    @FormField
    val id = FieldState(
        state = mutableStateOf<String?>(entry?.id ?: UUID.randomUUID().toString())  // Use String type for id
    )

    @FormField
    val description = FieldState(
        state = mutableStateOf<String?>(entry?.description),
        validators = mutableListOf(IsRequiredValidator(), MaxLengthValidator(100))  // Ensure description is required and within max length
    )

    @FormField
    val date = FieldState(
        state = mutableStateOf<Date?>(entry?.date),
        validators = mutableListOf(NotEmptyValidator())  // Ensure date is not empty
    )

    @FormField
    val startTime = FieldState(
        state = mutableStateOf<Hours?>(entry?.startTime?.toFullHours() ?: FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(IsRequiredValidator())  // Ensure start time is required
    )

    @FormField
    val endTime = FieldState(
        state = mutableStateOf<Hours?>(entry?.endTime?.toFullHours() ?: FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(
            IsRequiredValidator(),
            TimeValidator(
                minTime = { startTime.state.value!! },
                errorText = "End time can't be less than start time."
            )  // Ensure end time is after start time
        )
    )

    @FormField
    val category = FieldState(
        state = mutableStateOf<Category?>(entry?.category),
        optionItemFormatter = { it?.name ?: "" },
        validators = mutableListOf(NotEmptyValidator())  // Ensure category is not empty
    )

    @FormField
    val photograph = FieldState(
        state = mutableStateOf<Bitmap?>(entry?.photograph)
    )

    /**
     * Produce a TimeEntry object from the current form state.
     * @return A TimeEntry object with the form data.
     */
    fun produceEntry(): TimeEntry {
        val startTimeValue = this.startTime.state.value!!
        val endTimeValue = this.endTime.state.value!!

        return TimeEntry(
            id = this.id.state.value!!,  // Use the existing ID or generate a new one
            description = this.description.state.value!!,  // Description from the form state
            date = this.date.state.value!!,  // Date from the form state
            startTime = SerializableHours.fromFullHours(startTimeValue),  // Convert start time to SerializableHours
            endTime = SerializableHours.fromFullHours(endTimeValue),  // Convert end time to SerializableHours
            duration = SerializableHours.fromFullHours(
                FullHours(
                    hours = endTimeValue.hours - startTimeValue.hours,
                    minutes = endTimeValue.minutes - startTimeValue.minutes
                )
            ),  // Calculate duration
            category = this.category.state.value!!,  // Category from the form state
            photograph = this.photograph.state.value,  // Photograph from the form state
            isArchived = false  //archive state
        )
    }
}
