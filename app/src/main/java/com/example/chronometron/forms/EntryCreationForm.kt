package com.example.chronometron.forms

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.NotEmptyValidator
import co.yml.charts.common.extensions.isNotNull
import com.example.chronometron.forms.validators.IsRequiredValidator
import com.example.chronometron.forms.validators.MaxLengthValidator
import com.example.chronometron.forms.validators.TimeValidator
import com.example.chronometron.types.Category
import com.example.chronometron.types.FirebaseHours
import com.example.chronometron.types.TimeEntry
import com.example.chronometron.utils.BitmaptoString
import com.example.chronometron.utils.StringtoBitmap
import java.util.Date


class EntryCreationForm(entry: TimeEntry?) : Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val id = FieldState(
        state = mutableStateOf<String?>(entry?.id)
    )

    @FormField
    val description = FieldState(
        state = mutableStateOf<String?>(entry?.description),
        validators = mutableListOf(IsRequiredValidator(), MaxLengthValidator(100))
    )


    @FormField
    val date = FieldState(
        state = mutableStateOf<Date?>(entry?.date),
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )

    @FormField
    val startTime = FieldState(
        state = mutableStateOf<FirebaseHours>(
            entry?.startTime ?: FirebaseHours(
                Date().hours,
                Date().minutes
            )
        ),
        validators = mutableListOf(IsRequiredValidator())
    )

    @FormField
    val endTime = FieldState(
        state = mutableStateOf<FirebaseHours>(
            entry?.endTime ?: FirebaseHours(
                Date().hours,
                Date().minutes
            )
        ),
        validators = mutableListOf(
            IsRequiredValidator(),
            TimeValidator(
                minTime = { startTime.state.value },
                errorText = "End time can't be less than start time."
            )
        )
    )


    @FormField
    val category = FieldState(
        state = mutableStateOf<Category?>(entry?.category),
        optionItemFormatter = { "${it?.name}" },
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )

    @FormField
    val photograph = FieldState(
        state = mutableStateOf<Bitmap?>(if (entry?.photograph.isNotNull()) StringtoBitmap(entry?.photograph!!) else null)
    )

    fun produceEntry(isAlreadyArchived: Boolean = false): TimeEntry {
        var startTimeValue = this.startTime.state.value
        var endTimeValue = this.endTime.state.value

        return TimeEntry(
            id = if (this.id.state.value == null) "" else this.id.state.value!!,
            description = this.description.state.value!!,
            date = this.date.state.value!!,
            startTime = startTimeValue,
            endTime = endTimeValue,
            duration = FirebaseHours(
                hours = endTimeValue.hours!! - startTimeValue.hours!!,
                minutes = endTimeValue.minutes!! - startTimeValue.minutes!!
            ),
            category = this.category.state.value!!,
            photograph = if (this.photograph.state.value.isNotNull())
                BitmaptoString(this.photograph.state.value!!) else null,
            isArchived = isAlreadyArchived
        )
    }
}