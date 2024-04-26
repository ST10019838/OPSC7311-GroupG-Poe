package com.example.chronometron.ui.validators
import ch.benlu.composeform.Validator


class MaxLengthValidator(maxLength: Int, errorText: String? = null) : Validator<String?>(
    validate = {
        (it?.length ?: -1) <= maxLength
    },
    errorText = errorText ?: "This field is too long. Length should be less than $maxLength"
)