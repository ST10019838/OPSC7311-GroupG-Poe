package com.example.chronometron.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import ch.benlu.composeform.validators.EmailValidator
import ch.benlu.composeform.validators.MinLengthValidator
import com.example.chronometron.forms.validators.IsRequiredValidator
import com.example.chronometron.forms.validators.MaxLengthValidator

class LoginForm : Form() {

    override fun self(): Form {
        return this
    }

    @FormField
    val email = FieldState(
        state = mutableStateOf<String?>(""),
        validators = mutableListOf(
            IsRequiredValidator(),
            MaxLengthValidator(100),
            EmailValidator()
        )
    )

    @FormField
    val password = FieldState(
        state = mutableStateOf<String?>(""),
        validators = mutableListOf(
            IsRequiredValidator(),
        )
    )
}