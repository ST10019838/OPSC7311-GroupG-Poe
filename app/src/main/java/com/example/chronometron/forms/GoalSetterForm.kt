package com.example.chronometron.forms

import androidx.compose.runtime.mutableStateOf
import ch.benlu.composeform.FieldState
import ch.benlu.composeform.Form
import ch.benlu.composeform.FormField
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.example.chronometron.forms.validators.IsRequiredValidator
import com.example.chronometron.forms.validators.TimeValidator

class GoalSetterForm(minimumGoal: Hours, maximumGoal: Hours): Form() {
    override fun self(): Form {
        return this
    }

    @FormField
    val minGoal = FieldState(
        state = mutableStateOf<Hours?>(minimumGoal),
        validators = mutableListOf(IsRequiredValidator())
    )

    @FormField
    val maxGoal = FieldState(
        state = mutableStateOf<Hours?>(maximumGoal),
        validators = mutableListOf(
            IsRequiredValidator(),
            TimeValidator(
                minTime = { minGoal.state.value!! },
                errorText = "Maximum Goal can't be less than Minimum Goal"
            )
        )
    )
}
