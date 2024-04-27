package com.example.chronometron.ui.validators
import ch.benlu.composeform.Validator


class IsRequiredValidator<T>(errorText: String? = null) : Validator<T>(
    validate = {
        it != null && it != ""
    },
    errorText = errorText ?: "This field should not be empty"
)
