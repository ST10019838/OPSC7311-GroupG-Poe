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

class EntryCreationForm(entry: TimeEntry?) : Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val id = FieldState(
        state = mutableStateOf<String?>(entry?.id)  // Updated to String type
    )

    @FormField
    val description = FieldState(
        state = mutableStateOf<String?>(entry?.description),
        validators = mutableListOf(IsRequiredValidator(), MaxLengthValidator(100))
    )

    @FormField
    val date = FieldState(
        state = mutableStateOf<Date?>(entry?.date),
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val startTime = FieldState(
        state = mutableStateOf<Hours?>(entry?.startTime?.toFullHours() ?: FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(IsRequiredValidator())
    )

    @FormField
    val endTime = FieldState(
        state = mutableStateOf<Hours?>(entry?.endTime?.toFullHours() ?: FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(
            IsRequiredValidator(),
            TimeValidator(
                minTime = { startTime.state.value!! },
                errorText = "End time can't be less than start time."
            )
        )
    )

    @FormField
    val category = FieldState(
        state = mutableStateOf<Category?>(entry?.category),
        optionItemFormatter = { "${it?.name}" },
        validators = mutableListOf(NotEmptyValidator())
    )

    @FormField
    val photograph = FieldState(
        state = mutableStateOf<Bitmap?>(entry?.photograph)
    )

    fun produceEntry(): TimeEntry {
        val startTimeValue = this.startTime.state.value!!
        val endTimeValue = this.endTime.state.value!!

        return TimeEntry(
            id = this.id.state.value ?: UUID.randomUUID().toString(),  // Generate a UUID string if null
            description = this.description.state.value!!,
            date = this.date.state.value!!,
            startTime = SerializableHours.fromFullHours(startTimeValue),
            endTime = SerializableHours.fromFullHours(endTimeValue),
            duration = SerializableHours.fromFullHours(
                FullHours(
                    hours = endTimeValue.hours - startTimeValue.hours,
                    minutes = endTimeValue.minutes - startTimeValue.minutes
                )
            ),
            category = this.category.state.value!!,
            photograph = this.photograph.state.value
        )
    }
}
