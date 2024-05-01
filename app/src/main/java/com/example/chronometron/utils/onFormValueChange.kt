package com.example.chronometron.utils

import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form

// The following code was adapted from GitHub
// Author: benjamin-luescher
// Link: https://github.com/benjamin-luescher/compose-form/blob/main/composeform/src/main/java/ch/benlu/composeform/components/TextFieldComponent.kt
fun <T> onFormValueChange(value: Any?, form: Form, fieldState: FieldState<T?>) {
    @Suppress("UNCHECKED_CAST")
    fieldState.state.value = value as T?
    fieldState.hasChanges.value = true

    form.validate()
}