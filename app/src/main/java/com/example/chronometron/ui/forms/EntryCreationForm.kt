package com.example.chronometron.ui.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.NotEmptyValidator
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.Category
import com.example.chronometron.ui.validators.IsRequiredValidator
import com.example.chronometron.ui.validators.MaxLengthValidator
import com.example.chronometron.ui.validators.TimeValidator
import java.util.Date


class EntryCreationForm() : Form() {
    override fun self(): Form {
        return this
    }


    @FormField
    val description = FieldState(
        state = mutableStateOf<String?>(null),
        validators = mutableListOf(IsRequiredValidator(), MaxLengthValidator(100))
    )


    @FormField
    val date = FieldState(
        state = mutableStateOf<Date?>(null),
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )

    @FormField
    val startTime = FieldState(
        state = mutableStateOf<Hours?>(FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(IsRequiredValidator())
    )

    @FormField
    val endTime = FieldState(
        state = mutableStateOf<Hours?>(FullHours(Date().hours, Date().minutes)),
        validators = mutableListOf(
            IsRequiredValidator(),
            TimeValidator(
                minTime = { startTime.state.value!! },
                errorText = "End time cant be before start time."
            )
        )
    )

//    @FormField
//    val duration = FieldState(
//        state = mutableStateOf<Hours?>(
//            FullHours(
//                startTime.state.value!! - endTime.state.value!!,
//                startTime.state.value!! - endTime.state.value?.minutes!!
//            )
//        ),
//        validators = mutableListOf(
//            IsRequiredValidator(),
//            TimeValidator(
//                minTime = { startTime.state.value!! },
//                errorText = "End time cant be before start time."
//            )
//        )
//    )


    @FormField
    val category = FieldState(
        state = mutableStateOf<Category?>(null),
        options = mutableListOf(
            Category(id = 1, name = "Switzerland"),
            Category(id = 2, name = "Switzerland1"),
            Category(id = 3, name = "Switzerland2"),
        ),
        optionItemFormatter = { "${it?.name}" },
        validators = mutableListOf(
            NotEmptyValidator()
        )
    )

    @FormField
    val photograph = FieldState(
        state = mutableStateOf<String?>(null)
    )
}