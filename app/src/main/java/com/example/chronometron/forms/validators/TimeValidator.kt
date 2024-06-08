package com.example.chronometron.forms.validators

import ch.benlu.composeform.Validator
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.types.FirebaseHours

class TimeValidator(minTime: () -> FirebaseHours, errorText: String? = null) : Validator<FirebaseHours>(
    validate = {
        (it?.hours ?: -1) > minTime().hours!! ||
                (it?.hours ?: -1) == minTime().hours && (it?.minutes ?: -1) >= minTime().minutes!!
    },
    errorText = errorText ?: "This field is not valid."
)