package com.example.chronometron.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.ui.viewModels.UserSession


class GoalSettingForm : Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val minimumGoal = FieldState(
        state = mutableStateOf<Hours?>(UserSession.minimumGoal.value),
    )

    @FormField
    val maximumGoal = FieldState(
        state = mutableStateOf<Hours?>(UserSession.maximumGoal.value),
    )

}