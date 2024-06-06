package com.example.chronometron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chronometron.forms.GoalSetterForm
import com.example.chronometron.ui.composables.CategoryChip
import com.example.chronometron.ui.composables.CategoryCreationDialog
import com.example.chronometron.ui.composables.CollapsibleSection
import com.example.chronometron.ui.composables.formFields.TimeSelector
import com.example.chronometron.ui.viewModels.UserSession
import com.example.chronometron.utils.onFormValueChange

@Composable
fun CustomiserScreen() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
        item {
            Goals()
        }

        item {
            Categories()
        }
    }
}

@Composable
private fun Goals() {
    val minGoal by UserSession.minimumGoal.collectAsStateWithLifecycle()
    val maxGoal by UserSession.maximumGoal.collectAsStateWithLifecycle()

    var form = GoalSetterForm(minimumGoal = minGoal, maximumGoal = maxGoal)
    form.validate(true)

    CollapsibleSection(
        heading = "Daily Goals",
        icon = { Icon(Icons.Default.TaskAlt, contentDescription = "Goals") }) {
        Column(verticalArrangement = Arrangement.spacedBy(35.dp)) {
            TimeSelector(
                label = "Minimum Goal",
                value = form.minGoal.state.value,
                onChange = {
                    onFormValueChange(
                        value = it,
                        form = form,
                        fieldState = form.minGoal
                    )
                    UserSession.updateMinimumGoal(it)
                },
                hasError = form.minGoal.hasError(),
                errorText = form.minGoal.errorText,
            )

            TimeSelector(
                label = "Maximum Goal",
                value = form.maxGoal.state.value,
                onChange = {
                    onFormValueChange(
                        value = it,
                        form = form,
                        fieldState = form.maxGoal
                    )
                    if (form.isValid) {
                        UserSession.updateMaximumGoal(it)
                    }
                },
                hasError = form.maxGoal.hasError(),
                errorText = form.maxGoal.errorText,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Categories() {
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val categories by UserSession.categories.collectAsState()

    CollapsibleSection(
        heading = "Categories",
        icon = { Icon(Icons.Default.Category, contentDescription = "Categories") }) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (categories.isEmpty()) {
                Text(
                    "No Categories Created",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        CategoryChip(
                            category.name.orEmpty(),
                            modifier = Modifier.padding(horizontal = 5.dp),
                            confirmationAction = {
                                UserSession.removeCategory(category)
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { isDialogOpen = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Category")
            }
        }

        if (isDialogOpen) {
            CategoryCreationDialog(onDismiss = {
                isDialogOpen = false
            })
        }
    }

}
